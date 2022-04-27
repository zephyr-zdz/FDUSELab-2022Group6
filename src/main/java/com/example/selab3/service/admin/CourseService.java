package com.example.selab3.service.admin;

import com.example.selab3.manager.admin.CourseManager;
import com.example.selab3.model.entity.Calendar;
import com.example.selab3.model.entity.Course;
import com.example.selab3.model.entity.CourseApplication;
import com.example.selab3.model.entity.Schedule;
import com.example.selab3.model.vo.CourseApplicationVO;
import com.example.selab3.model.vo.CourseVO;
import com.example.selab3.util.Response;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.aspectj.util.LangUtil.split;

@Transactional
@Service("AdminCourseService")
public class CourseService {
    private final CourseManager courseManager;

    @Autowired
    CourseService(CourseManager courseManager){
        this.courseManager=courseManager;
    }

    Course fromApplicationToCourse(CourseApplication courseApplication){
        Course course=new Course();
        course.setName(courseApplication.getCoursename());
        course.setCoursehour(courseApplication.getCoursehour());
        course.setCoursenum(courseApplication.getCoursenum());
        course.setCredit(courseApplication.getCredit());
        course.setCapacity(courseApplication.getCapacity());
        course.setIntro(courseApplication.getIntro());
        return course;
    }

    List<Schedule> deleteScheduleByCourseId(Integer courseId){
        return courseManager.deleteSchedulesByCourseId(courseId);
    }

    void addSchedules(List<Schedule> schedules){
        courseManager.save(schedules);
    }

    List<Integer> getCalendarIDsFromApplication(CourseApplication courseApplication){
        String[] schedule=split(courseApplication.getSchedule());
        List<Integer> CalendarIdList = new ArrayList<>();
        for (String s : schedule){
            String[] info=s.split(",");
            CalendarIdList.add(courseManager.findCalendarIdByDayAndNumber(info[0],info[1]));
        }
        return CalendarIdList;
    }

    public String getTeacherJobnumById(Integer Id){
        return courseManager.findTeacherJobNumById(Id);
    }

    public Integer getTeacherIdByJobnum(String JobNum){
        return courseManager.findTeacherIdByJobNum(JobNum);
    }

    List<Schedule> getSchedulesFromApplication(CourseApplication courseApplication,Course course){
        List<Integer> CalendarIdList=getCalendarIDsFromApplication(courseApplication);
        List<Schedule> schedules = new ArrayList<>();
        for(Integer c : CalendarIdList) {
        Schedule schedule=new Schedule();
        schedule.setCalendarid(c);
        schedule.setCourseid(courseApplication.getPre_courseId());
        schedule.setClassroomid(courseApplication.getClassroomid());
        schedule.setTeacherid(courseApplication.getTeacherid());
        schedules.add(schedule);
        }
        return schedules;
    }

    boolean check(CourseApplication courseApplication){
        if(Objects.equals(courseApplication.getApplytype(), "delete")&&courseApplication.getPre_courseId()==-1) return false;
        if(Objects.equals(courseApplication.getApplytype(),"update")&&courseApplication.getPre_courseId()==-1) return false;

        List<Integer> CalendarIdListId=getCalendarIDsFromApplication(courseApplication);
        Integer ClassroomId=courseApplication.getClassroomid();
        Integer TeacherId=courseApplication.getTeacherid();

        // 得到被修改课程的上课时间、教室
        List<Schedule> schedules=courseManager.deleteSchedulesByCourseId(courseApplication.getPre_courseId());
        boolean flag=true;
        for (Integer i : CalendarIdListId){
            if(courseManager.scheduleExistByCalendarIdAndClassroomId(i,ClassroomId)||courseManager.scheduleExistByCalendarIdAndTeacherId(i,TeacherId)) {
                flag=false;
                System.out.println(i);
            }
        }
        addSchedules(schedules);
        return flag;
    }

    public Response<String> approve(CourseApplication courseApplication,boolean attitude){
        if (!attitude){
            courseApplication.setResult("reject");
            courseManager.save(courseApplication);
            return new Response<>(Response.SUCCESS,"Administrator not approve","disapproval");
        }
        Course course=fromApplicationToCourse(courseApplication);
        if(!check(courseApplication))
            return new Response<>(Response.FAIL,"err","Another application had been approved,leading to conflict");
        switch (courseApplication.getApplytype()){
            case "delete":{
                courseManager.delete(course);
                courseApplication.setResult("approve");
                courseManager.save(courseApplication);
                courseManager.deleteSchedulesByCourseId(courseApplication.getPre_courseId());
                return new Response<>(Response.SUCCESS,"删除申请处理成功","delete succeed");
            }
            case "update":{
                Course originCourse=courseManager.findCourseByCourseId(courseApplication.getPre_courseId());
                courseManager.deleteSchedulesByCourseId(courseApplication.getPre_courseId());
                originCourse.update(course);
                courseManager.save(originCourse);
                addSchedules(getSchedulesFromApplication(courseApplication,course));
                courseApplication.setResult("approve");
                courseManager.save(courseApplication);
                return new Response<>(Response.SUCCESS,"修改申请处理成功","update succeed");
            }
            case "insert":{
                courseManager.save(course);
                addSchedules(getSchedulesFromApplication(courseApplication,course));
                courseApplication.setResult("approve");
                courseManager.save(courseApplication);
                return new Response<>(Response.SUCCESS,"新增申请处理成功","insert succeed");
            }
            default:
                courseApplication.setResult("reject");
                courseManager.save(courseApplication);
                return new Response<>(Response.FAIL,"err","illegal application type");
        }
    }


    public Response<String> modify(CourseApplication courseApplication){
        if(!check(courseApplication)){
            courseApplication.setResult("reject");
            courseManager.save(courseApplication);
            return new Response<>(Response.FAIL,"conflict","conflict");
        }
        approve(courseApplication,true);
        return new Response<>(Response.SUCCESS,"success","modify success");
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
        CourseApplication courseApplication;
        Response<String> response;
        for (CsvRow row : container.getRows()) {
            count++;
            courseApplication = new CourseApplication();
            courseApplication.setResult(row.getField("result"));
            courseApplication.setCoursehour(row.getField("coursehour"));
            courseApplication.setCoursenum(row.getField("coursenum"));
            courseApplication.setCapacity(row.getField("coursenum"));
            courseApplication.setApplytype(row.getField("applytype"));
            courseApplication.setClassroomid(Integer.valueOf(row.getField("classroomid")));
            courseApplication.setCoursename(row.getField("coursename"));
            courseApplication.setApplytime(row.getField("applytime"));
            courseApplication.setIntro(row.getField("intro"));
            courseApplication.setInstituteid(Integer.valueOf(row.getField("instituteid")));
            courseApplication.setCredit(row.getField("credit"));
            courseApplication.setSchedule(row.getField("schedule"));
            courseApplication.setTeacherid(Integer.valueOf(row.getField("teacherid")));
            courseApplication.setResult(row.getField("Result"));
            response=modify(courseApplication);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count).append(",");
            }
        }
        return new Response<>(Response.SUCCESS, "批量创建课程申请成功", result.toString());
    }

    public Response<List<CourseApplicationVO>> showAllApplications(){
        return new Response<>(Response.SUCCESS,"success",courseManager.findAllCourseApplication());
    }

    public Response<List<CourseApplicationVO>> showPendingApplications() {
        return new Response<>(Response.SUCCESS, "success", courseManager.findAllPendingCourseApplication());
    }

    public Response<List<CourseVO>> showAllCourse(){
        return new Response<>(Response.SUCCESS,"success",courseManager.findAllCourse());
    }

    public Response<String> courseDetail(Course course){
            List<Schedule> schedules=courseManager.findSchedulesByCourseId(course.getId());
            Integer TeacherId= schedules.get(0).getTeacherid();
            String schedule="";
            String teacherNum=courseManager.findJobNumById(TeacherId);
            for(Schedule s : schedules){
                Calendar calendar=courseManager.findCalendarById(s.getCalendarid());
                String day=calendar.getDay();
                String number=calendar.getNumber();
                schedule=day+","+number;
            }
            Integer classroomId=schedules.get(0).getClassroomid();
            String classroomNum=courseManager.findClassroomNumById(classroomId);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Id",course.getId());
            jsonObject.put("Name",course.getName());
            jsonObject.put("Credit",course.getCredit());
            jsonObject.put("Hour",course.getCoursehour());
            jsonObject.put("Capacity",course.getCapacity());
            jsonObject.put("Coursenum",course.getCoursenum());
            jsonObject.put("Intro",course.getIntro());
            jsonObject.put("teacherNum",teacherNum);
            jsonObject.put("schedule",schedule);
            jsonObject.put("classroomNum",classroomNum);
            return new Response<>(Response.SUCCESS,"success",jsonObject.toString());
    }
}
