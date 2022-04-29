package com.example.selab3.manager.admin;

import com.example.selab3.mapper.*;
import com.example.selab3.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AdminTeacherManager")
public class TeacherManager {
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final InstituteMapper instituteMapper;
    private final MajorMapper majorMapper;
    private final ScheduleMapper scheduleMapper;
    private final CourseMapper courseMapper;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public TeacherManager(TeacherMapper teacherMapper, StudentMapper studentMapper,
                          InstituteMapper instituteMapper, MajorMapper majorMapper,
                          ScheduleMapper scheduleMapper, CourseMapper courseMapper,
                          ApplicationMapper applicationMapper) {
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.instituteMapper = instituteMapper;
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.courseMapper = courseMapper;
        this.applicationMapper = applicationMapper;
    }

    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAll();
    }

    public Teacher getTeacherByIdentity(String identity) {
        return teacherMapper.findTeacherByIdentity(identity);
    }

    public Teacher getTeacherById(Integer id) {
        return teacherMapper.findTeacherById(id);
    }

    public Teacher getTeacherByJobnum(String jobnum) {
        return teacherMapper.findTeacherByJobnum(jobnum);
    }

    public Institute getInstituteByName(String name) {
        return instituteMapper.findInstituteByName(name);
    }

    public Major getMajorByName(String name) {
        return majorMapper.findMajorByName(name);
    }

    public Student getStudentByIdentity(String identity) {
        return studentMapper.findStudentByIdentity(identity);
    }



    public void createTeacher(Teacher teacher) {
        teacherMapper.save(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherMapper.delete(teacher);
    }

    public void updateTeacher(Teacher teacher) {
        teacherMapper.save(teacher);
    }

    public List<Course> getCoursesByTeacher(Teacher teacher) {
        List<Schedule> schedules = scheduleMapper.findAllByTeacherid(teacher.getId());
        List<Course> courses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            Course course = courseMapper.findCourseById(schedule.getCourseid());
            courses.add(course);
        }
        return courses;
    }

    public List<CourseApplication> getCourseApplicationsByTeacher(Teacher teacher) {
        return applicationMapper.findAllByTeacherid(teacher.getId());
    }
}