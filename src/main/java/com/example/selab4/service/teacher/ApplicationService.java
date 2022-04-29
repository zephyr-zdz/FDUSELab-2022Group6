package com.example.selab4.service.teacher;
import com.example.selab4.manager.teacher.ApplicationManager;
import com.example.selab4.model.checker.CourseApplicationChecker;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.CourseApplication;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("TeacherApplicationService")
public class ApplicationService {
    private final ApplicationManager applicationManager;
    private final CourseApplicationChecker checker;

    @Autowired
    ApplicationService(ApplicationManager applicationManager, CourseApplicationChecker checker){
        this.applicationManager=applicationManager;
        this.checker = checker;
    }

    void addSchedules(List<Schedule> schedules){
        applicationManager.save(schedules);
    }

    String[] split(String schedule){
        return schedule.split(" ");
    }

    boolean check(CourseApplication courseApplication){
        // 字段非空检查
        if (!checker.infoComplete(courseApplication)) {
            return false;
        }

        Course course= applicationManager.findCourseById(courseApplication.getPre_courseId());
        switch (courseApplication.getApplytype()) {
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

        List<Schedule> schedules=applicationManager.deleteSchedulesByCourseId(courseApplication.getPre_courseId());
        String[] schedule=split(courseApplication.getSchedule());
        List<Integer> CalendarIdList = new ArrayList<>();
        for (String s : schedule){
            String[] info=s.split(",");
            CalendarIdList.add(applicationManager.getCalendarIdByDayAndNumber(info[0],info[1]));
        }
        Integer ClassroomId=courseApplication.getClassroomid();
        Integer TeacherId=courseApplication.getTeacherid();
        boolean flag=true;
        for (Integer i : CalendarIdList){
            if(applicationManager.scheduleExistByCalendarIdAndClassroomId(i,ClassroomId)||applicationManager.scheduleExistByCalendarIdAndTeacherId(i,TeacherId))
                flag=false;
        }
        addSchedules(schedules);
        return flag;
    }


    public Response<String> upload(CourseApplication courseApplication){
        if(check(courseApplication)){
            applicationManager.save(courseApplication);
            return new Response<>(Response.SUCCESS,"success","application uploaded");
        }
        else
            return new Response<>(Response.FAIL,"err","conflict");
    }

    public Response<List<CourseApplication>> showMyApplication(String JobNum){
        List<CourseApplication> courseApplications=applicationManager.getCourseApplicationByJobNum(JobNum);
        return new Response<>(Response.SUCCESS,"success",courseApplications);
    }
}
