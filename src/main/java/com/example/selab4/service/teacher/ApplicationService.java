package com.example.selab4.service.teacher;
import com.example.selab4.manager.teacher.ApplicationManager;
import com.example.selab4.model.checker.TeacherCourseApplicationChecker;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.entity.Schedule;
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
        // 字段非空检查
        if (!checker.infoComplete(teacherCourseApplication)) {
            return false;
        }

        Course course= applicationManager.findCourseById(teacherCourseApplication.getPre_courseId());
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

        List<Schedule> schedules=applicationManager.deleteSchedulesByCourseId(teacherCourseApplication.getPre_courseId());
        String[] schedule=split(teacherCourseApplication.getSchedule());
        List<Integer> CalendarIdList = new ArrayList<>();
        for (String s : schedule){
            String[] info=s.split(",");
            CalendarIdList.add(applicationManager.getCalendarIdByDayAndNumber(info[0],info[1]));
        }
        Integer ClassroomId= teacherCourseApplication.getClassroomid();
        Integer TeacherId= teacherCourseApplication.getTeacherid();
        boolean flag=true;
        for (Integer i : CalendarIdList){
            if(applicationManager.scheduleExistByCalendarIdAndClassroomId(i,ClassroomId)||applicationManager.scheduleExistByCalendarIdAndTeacherId(i,TeacherId))
                flag=false;
        }
        addSchedules(schedules);
        Integer classroomId= teacherCourseApplication.getClassroomid();
        String classroomCapacity=applicationManager.findClassroomCapacityById(classroomId);
        if(applicationManager.findClassroomById(teacherCourseApplication.getClassroomid()).getState().equals("off")) {
            throw new RuntimeException("classroom is off");
        }
        if(parseInt(teacherCourseApplication.getCapacity()) > parseInt(classroomCapacity)) {
            throw new RuntimeException("capacity overflow");
        }
        return flag;
    }


    public Response<String> upload(TeacherCourseApplication teacherCourseApplication){
        if(!check(teacherCourseApplication)){
            return new Response<>(Response.FAIL,"err","conflict");
        }
        else {
            applicationManager.save(teacherCourseApplication);
            return new Response<>(Response.SUCCESS,"success","application uploaded");
        }
    }

    public Response<List<TeacherCourseApplication>> showMyApplication(String JobNum){
        List<TeacherCourseApplication> teacherCourseApplications =applicationManager.getCourseApplicationByJobNum(JobNum);
        return new Response<>(Response.SUCCESS,"success", teacherCourseApplications);
    }
}
