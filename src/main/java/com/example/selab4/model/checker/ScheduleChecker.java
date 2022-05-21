package com.example.selab4.model.checker;

import com.example.selab4.mapper.ScheduleMapper;
import com.example.selab4.mapper.StuCourseMapper;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.model.entity.StuCourse;
import com.example.selab4.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ScheduleChecker {
    private final ScheduleMapper scheduleMapper;
    private final StuCourseMapper stuCourseMapper;

    @Autowired
    public ScheduleChecker(ScheduleMapper scheduleMapper, StuCourseMapper stuCourseMapper) {
        this.scheduleMapper = scheduleMapper;
        this.stuCourseMapper = stuCourseMapper;
    }


    private boolean feasible(List<Schedule> studentSchedules,List<Schedule> newSchedules){
        for(Schedule newSchedule : newSchedules){
            for(Schedule studentSchedule : studentSchedules){
                if(Objects.equals(studentSchedule.getCalendarid(), newSchedule.getCalendarid()))
                    return false;
            }
        }
        return true;
    }

    public boolean checkSchedule(Student student, Course course) {
        List<StuCourse> stuCourseList = stuCourseMapper.findStuCoursesByStudentidAndStatus(student.getId(), "S");
        List<Schedule> studentScheduleList = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            List<Schedule> schedules = scheduleMapper.findSchedulesByCourseid(stuCourse.getCourseid());
            studentScheduleList.addAll(schedules);
        }

        List<Schedule> courseScheduleList = scheduleMapper.findSchedulesByCourseid(course.getId());
        return feasible(studentScheduleList,courseScheduleList);
    }

}
