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

@Component
public class ScheduleChecker {
    private final ScheduleMapper scheduleMapper;
    private final StuCourseMapper stuCourseMapper;

    @Autowired
    public ScheduleChecker(ScheduleMapper scheduleMapper, StuCourseMapper stuCourseMapper) {
        this.scheduleMapper = scheduleMapper;
        this.stuCourseMapper = stuCourseMapper;
    }

    public boolean checkSchedule(Student student, Course course) {
        List<StuCourse> stuCourseList = stuCourseMapper.findStuCoursesByStudentidAndStatus(student.getId(), "S");
        List<Schedule> studentScheduleList = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            List<Schedule> schedules = scheduleMapper.findSchedulesByCourseid(stuCourse.getCourseid());
            studentScheduleList.addAll(schedules);
        }

        List<Schedule> courseScheduleList = scheduleMapper.findSchedulesByCourseid(course.getId());
        for (Schedule schedule : courseScheduleList) {
            if (studentScheduleList.contains(schedule)) {   // 挑选的课程的某个上课时间，学生本来就要上课
                return false;
            }
        }

        return true;
    }

}
