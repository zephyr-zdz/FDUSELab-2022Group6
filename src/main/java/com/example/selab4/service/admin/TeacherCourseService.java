package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.TeacherCourseManager;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.util.Response;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static org.aspectj.util.LangUtil.split;

@Transactional
@Service("AdminTeacherCourseService")
public class TeacherCourseService {
    private final TeacherCourseManager manager;

    @Autowired
    TeacherCourseService(TeacherCourseManager teacherCourseManager){
        this.manager = teacherCourseManager;
    }

    Course fromApplicationToCourse(TeacherCourseApplication teacherCourseApplication){
        Course course = new Course();
        course.setCoursehour(teacherCourseApplication.getCoursehour());
        course.setCredit(teacherCourseApplication.getCredit());
        course.setCapacity(teacherCourseApplication.getCapacity());
        course.setIntro(teacherCourseApplication.getIntro());
        course.setIspublic(teacherCourseApplication.getIspublic());
        course.setSemester(teacherCourseApplication.getSemester());

        CourseTemplate courseTemplate = manager.getCourseTemplateById(teacherCourseApplication.getCoursetemplateid());

        course.setCoursetemplateid(courseTemplate.getId());

        if (teacherCourseApplication.getPrecourseid() == -1) { // insert
            course.setCurrentcount("0");
        } else {    // delete or update
            course.setId(teacherCourseApplication.getPrecourseid());
            course.setCurrentcount(manager.findCourseByCourseId(teacherCourseApplication.getPrecourseid()).getCapacity());
        }

        return course;
    }

    void addSchedules(List<Schedule> schedules){
        manager.save(schedules);
    }

    List<Integer> getCalendarIDsFromApplication(TeacherCourseApplication teacherCourseApplication){
        String[] schedule=split(teacherCourseApplication.getSchedule());
        List<Integer> CalendarIdList = new ArrayList<>();
        for (String s : schedule){
            String[] info=s.split(",");
            CalendarIdList.add(manager.findCalendarIdByDayAndNumber(info[0],info[1]));
        }
        return CalendarIdList;
    }

    public String getTeacherJobnumById(Integer Id){
        return manager.findTeacherJobNumById(Id);
    }

    public Integer getTeacherIdByJobnum(String JobNum){
        return manager.findTeacherIdByJobNum(JobNum);
    }

    List<Schedule> getSchedulesFromApplication(TeacherCourseApplication teacherCourseApplication){
        List<Integer> CalendarIdList = getCalendarIDsFromApplication(teacherCourseApplication);
        List<Schedule> schedules = new ArrayList<>();
        for(Integer c : CalendarIdList) {
            Schedule schedule = new Schedule();
            schedule.setCalendarid(c);
            schedule.setCourseid(teacherCourseApplication.getPrecourseid());
            schedule.setClassroomid(teacherCourseApplication.getClassroomid());
            schedule.setTeacherid(teacherCourseApplication.getTeacherid());
            schedules.add(schedule);
        }
        return schedules;
    }

    boolean check(TeacherCourseApplication teacherCourseApplication){
        // 1、检查申请的类型与pre_courseid的情形是否符合预期
        Course course = manager.findCourseByCourseId(teacherCourseApplication.getPrecourseid());
        switch (teacherCourseApplication.getApplytype()) {
            case "delete" : case "update" :
                if (course == null) {
                    return false;
                }
                break;
            case "insert" :
                if (course != null) {
                    return false;
                }
                break;
            default :
                return false;
        }

        // 2、检查申请的上课时间内，其上课地点是否在上其他的课
        // 3、检查申请的上课时间内，其上课教师是否也在上其他的课
        List<Integer> CalendarIdListId=getCalendarIDsFromApplication(teacherCourseApplication);
        Integer ClassroomId= teacherCourseApplication.getClassroomid();
        Integer TeacherId= teacherCourseApplication.getTeacherid();

        // 得到被修改课程的上课时间、教室
        List<Schedule> schedules= manager.deleteSchedulesByCourseId(teacherCourseApplication.getPrecourseid());
        boolean flag=true;
        for (Integer i : CalendarIdListId){
            if(manager.scheduleExistByCalendarIdAndClassroomId(i,ClassroomId)|| manager.scheduleExistByCalendarIdAndTeacherId(i,TeacherId)) {
                flag=false;
                System.out.println(i);
            }
        }
        addSchedules(schedules);


        Integer classroomId= teacherCourseApplication.getClassroomid();
        String classroomCapacity= manager.findClassroomCapacityById(classroomId);

        // 4、教室的状态为on
        if(manager.findClassroomById(teacherCourseApplication.getClassroomid()).getState().equals("off")) {
            throw new RuntimeException("classroom is off");
        }

        // 5、教室的capacity >= 课程的capacity（老师不占容量）
        if(parseInt(teacherCourseApplication.getCapacity()) > parseInt(classroomCapacity)) {
            throw new RuntimeException("capacity overflow");
        }
        return flag;
    }

    public Response<String> approve(TeacherCourseApplication teacherCourseApplication, boolean attitude){
        if (!attitude){ // 管理员不通过申请
            teacherCourseApplication.setResult("reject");
            manager.save(teacherCourseApplication);
            return new Response<>(Response.SUCCESS,"课程申请处理成功，管理员不通过申请","disapproval");
        }
        Course course = fromApplicationToCourse(teacherCourseApplication);

        // 一些逻辑检查
        if(!check(teacherCourseApplication))
            return new Response<>(Response.FAIL,"产生逻辑错误，管理员不得通过申请","Another application had been approved,leading to conflict");

        switch (teacherCourseApplication.getApplytype()){
            case "delete":{
                // 更新Course
                manager.delete(course);
                // 更新TeacherCourseApplication
                teacherCourseApplication.setResult("approve");
                manager.save(teacherCourseApplication);
                // 更新Schedule
                manager.deleteSchedulesByCourseId(teacherCourseApplication.getPrecourseid());
                // TODO StuCourse级联删除
                manager.deleteStuCourseByCourseid(teacherCourseApplication.getPrecourseid());
                // TODO CourseAndMajor级联删除
                manager.deleteCourseAndMajorByCourseid(teacherCourseApplication.getPrecourseid());
                // TODO StudentCourseApplication相关的被reject
                manager.rejectStudentApplicationByCourseid(teacherCourseApplication.getPrecourseid());
                // TODO TeacherCourseApplication相关的被reject
                manager.rejectTeacherCourseApplicationByPrecourseid(teacherCourseApplication.getPrecourseid());
                return new Response<>(Response.SUCCESS,"管理员通过申请，删除申请处理成功","delete succeed");
            }
            case "update":{
                // 更新Schedule
                manager.deleteSchedulesByCourseId(teacherCourseApplication.getPrecourseid());
                addSchedules(getSchedulesFromApplication(teacherCourseApplication));
                // 更新Course
                manager.save(course);
                // 更新TeacherCourseApplication
                teacherCourseApplication.setResult("approve");
                manager.save(teacherCourseApplication);
                // 课程可选专业不允许修改（忽视majoridlist，逻辑检查保证ispublic不会变），CourseAndMajor不变
                // StuCourse不变
                // TODO StudentCourseApplication相关的被reject
                manager.rejectStudentApplicationByCourseid(teacherCourseApplication.getPrecourseid());
                // TeacherCourseApplication不变
                return new Response<>(Response.SUCCESS,"管理员通过申请，修改申请处理成功","update succeed");
            }
            case "insert":{
                // 更新Course
                manager.save(course);
                // 更新Schedule
                addSchedules(getSchedulesFromApplication(teacherCourseApplication));
                // 更新TeacherCourseApplication
                teacherCourseApplication.setResult("approve");
                manager.save(teacherCourseApplication);
                // TODO 写入CourseAndMajor
                manager.addCourseAndMajor(course.getId(), teacherCourseApplication.getMajoridlist());
                // 由于是新创建的课程，其id不可能与学生/教师的选课申请或学生选课情况相关联
                return new Response<>(Response.SUCCESS,"管理员通过申请，新增申请处理成功","insert succeed");
            }
            default:
                teacherCourseApplication.setResult("reject");
                manager.save(teacherCourseApplication);
                return new Response<>(Response.FAIL,"课程申请的类型非法，管理员不得通过申请","illegal application type");
        }
    }


    public Response<String> modify(TeacherCourseApplication teacherCourseApplication){
        if(!check(teacherCourseApplication)){
            teacherCourseApplication.setResult("reject");
            manager.save(teacherCourseApplication);
            return new Response<>(Response.FAIL,"产生逻辑错误，管理员不得修改课程，","conflict");
        }

        approve(teacherCourseApplication,true);
        return new Response<>(Response.SUCCESS,"管理员修改课程成功","modify success");
    }

    public Response<String> BatchModify(MultipartFile multipartFile) {
        CsvReader reader = new CsvReader();
        reader.setContainsHeader(true);
        File file = null;
        if (multipartFile != null){
            try {
                file = File.createTempFile("tmp", null);
                multipartFile.transferTo(file);
                System.gc();
                file.deleteOnExit();
            } catch (Exception e){
                return new Response<>(Response.FAIL, "multipartFile转换为File时发生Exception异常", null);
            }
        }

        CsvContainer container;
        try {
            assert file != null;
            container = reader.read(file, StandardCharsets.UTF_8);
        } catch(IOException e) {
            return new Response<>(Response.FAIL, "csv文件读取时发生IOException异常", null);
        }

        StringBuilder result = new StringBuilder("失败序号: ");
        int count = 0;
        TeacherCourseApplication teacherCourseApplication;
        Response<String> response;
        for (CsvRow row : container.getRows()) {
            count++;
            teacherCourseApplication = new TeacherCourseApplication();
            teacherCourseApplication.setResult(row.getField("result"));
            teacherCourseApplication.setCoursehour(row.getField("coursehour"));
            teacherCourseApplication.setCoursetemplateid(Integer.parseInt(row.getField("coursetemplateid")));
            teacherCourseApplication.setCapacity(row.getField("capacity"));
            teacherCourseApplication.setApplytype(row.getField("applytype"));
            teacherCourseApplication.setClassroomid(Integer.parseInt(row.getField("classroomid")));
            teacherCourseApplication.setApplytime(row.getField("applytime"));
            teacherCourseApplication.setIntro(row.getField("intro"));
            teacherCourseApplication.setCredit(row.getField("credit"));
            teacherCourseApplication.setSchedule(row.getField("schedule"));
            teacherCourseApplication.setTeacherid(Integer.parseInt(row.getField("teacherid")));
            teacherCourseApplication.setIspublic(row.getField("ispublic"));
            response=modify(teacherCourseApplication);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count).append(",");
            }
        }
        return new Response<>(Response.SUCCESS, "批量创建课程申请成功", result.toString());
    }

    public Response<List<TeacherCourseApplicationVO>> showAllApplications(){
        return new Response<>(Response.SUCCESS,"success", manager.findAllCourseApplication());
    }

    public Response<List<TeacherCourseApplicationVO>> showPendingApplications() {
        return new Response<>(Response.SUCCESS, "success", manager.findAllPendingCourseApplication());
    }

    public Response<List<CourseVO>> showAllCourse(){
        return new Response<>(Response.SUCCESS,"success", manager.findAllCourse());
    }
}
