package com.example.selab3.service.teacher;
import com.example.selab3.manager.teacher.ApplicationManager;
import com.example.selab3.model.entity.Course;
import com.example.selab3.model.entity.CourseApplication;
import com.example.selab3.model.entity.Schedule;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("TeacherApplicationService")
public class ApplicationService {
    private final ApplicationManager applicationManager;

    @Autowired
    ApplicationService(ApplicationManager applicationManager){
        this.applicationManager=applicationManager;
    }

    void addSchedules(List<Schedule> schedules){
        applicationManager.save(schedules);
    }

    String[] split(String schedule){
        return schedule.split(" ");
    }

    boolean check(CourseApplication courseApplication){
        Course course= applicationManager.findCourseById(courseApplication.getPre_courseId());
        if(Objects.equals(courseApplication.getApplytype(), "delete")&&course==null) return false;
        if(Objects.equals(courseApplication.getCoursenum(),"update")&&course==null)  return false;

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
