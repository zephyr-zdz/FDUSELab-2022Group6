package com.example.selab4.service.teacher;
import com.example.selab4.manager.teacher.ApplicationManager;
import com.example.selab4.model.checker.TeacherCourseApplicationChecker;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Transactional
@Service("TeacherApplicationService")
public class ApplicationService {
    private final ApplicationManager applicationManager;
    private final TeacherCourseApplicationChecker checker;

    @Autowired
    ApplicationService(ApplicationManager applicationManager, TeacherCourseApplicationChecker checker){
        this.applicationManager=applicationManager;
        this.checker = checker;
    }

    void addSchedules(List<Schedule> schedules){
        applicationManager.save(schedules);
    }

    String[] split(String schedule){
        return schedule.split(" ");
    }

    boolean check(TeacherCourseApplication teacherCourseApplication){
        Course course= applicationManager.findCourseById(teacherCourseApplication.getPrecourseid());
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

        // 1、同一学期的时空检查，同一学期的人时检查
        String[] schedule=split(teacherCourseApplication.getSchedule());
        List<Integer> CalendarIdList = new ArrayList<>();
        for (String s : schedule){
            String[] info=s.split(",");
            CalendarIdList.add(applicationManager.getCalendarIdByDayAndNumber(info[0],info[1]));
        }

        Integer ClassroomId= teacherCourseApplication.getClassroomid();
        Integer TeacherId= teacherCourseApplication.getTeacherid();
        String semester = teacherCourseApplication.getSemester();

        List<Schedule> schedules=applicationManager.deleteSchedulesByCourseId(teacherCourseApplication.getPrecourseid());
        boolean flag=true;
        for (Integer i : CalendarIdList){
            if(applicationManager.scheduleExistByCalendarIdAndClassroomIdAndSemester(i,ClassroomId, semester)||applicationManager.scheduleExistByCalendarIdAndTeacherIdAndSemester(i,TeacherId,semester))
                flag=false;
        }
        addSchedules(schedules);

        if (!flag) {
            return false;
        }

        // 2、教师on检查
        Integer classroomId= teacherCourseApplication.getClassroomid();
        String classroomCapacity=applicationManager.findClassroomCapacityById(classroomId);
        if(applicationManager.findClassroomById(teacherCourseApplication.getClassroomid()).getState().equals("off")) {
            return false;
        }

        // 3、capacity检查
        if(parseInt(teacherCourseApplication.getCapacity()) > parseInt(classroomCapacity)) {
            return false;
        }

        // 4、update类型的申请，ispublic不能修改
        if (teacherCourseApplication.getApplytype().equals("update")) {
            if (applicationManager.changeIspublic(teacherCourseApplication)) {
                return false;
            }
        }

        return true;
    }


    public Response<String> upload(TeacherCourseApplication teacherCourseApplication){
        // 预检查
        if(!check(teacherCourseApplication)){
            return new Response<>(Response.FAIL,"错误","发生逻辑错误");
        }
        else {
            teacherCourseApplication.setResult("pending");
            applicationManager.save(teacherCourseApplication);
            return new Response<>(Response.SUCCESS,"成功","申请已上传");
        }
    }

    public Response<List<TeacherCourseApplicationVO>> getApplicationsByTeacherid(Integer teacherid){
        return new Response<>(Response.SUCCESS,"成功", applicationManager.getApplicationsByTeacherid(teacherid));
    }
}
