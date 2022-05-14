package com.example.selab3.service.teacher;
import com.example.selab3.manager.teacher.ApplicationManager;
import com.example.selab3.model.entity.Course;
import com.example.selab3.model.entity.CourseApplication;
import com.example.selab3.model.entity.Schedule;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Transactional
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
        Integer classroomId=courseApplication.getClassroomid();
        String classroomCapacity=applicationManager.findClassroomCapacityById(classroomId);
        if(applicationManager.findClassroomById(courseApplication.getClassroomid()).getState().equals("off")) {
            throw new RuntimeException("classroom is off");
        }
        if(parseInt(courseApplication.getCapacity()) > parseInt(classroomCapacity)) {
            throw new RuntimeException("capacity overflow");
        }
        return flag;
    }


    public Response<String> upload(CourseApplication courseApplication){
        try {
            if(check(courseApplication)){
                applicationManager.save(courseApplication);
                return new Response<>(Response.SUCCESS,"success","application uploaded");
            }
            else
                return new Response<>(Response.FAIL,"err","conflict");
        } catch (RuntimeException e) {
            return new Response<>(Response.FAIL, e.toString(), "catch异常");
        }
    }

    public Response<List<CourseApplication>> showMyApplication(String JobNum){
        List<CourseApplication> courseApplications=applicationManager.getCourseApplicationByJobNum(JobNum);
        return new Response<>(Response.SUCCESS,"success",courseApplications);
    }
}
