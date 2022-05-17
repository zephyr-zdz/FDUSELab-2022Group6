package com.example.selab4.manager.admin;

import com.example.selab4.mapper.*;
import com.example.selab4.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AdminStudentManager")
public class StudentManager {
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final InstituteMapper instituteMapper;
    private final MajorMapper majorMapper;
    private final StuCourseMapper stuCourseMapper;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentManager(StudentMapper studentMapper, TeacherMapper teacherMapper,
                          InstituteMapper instituteMapper, MajorMapper majorMapper,
                          StuCourseMapper stuCourseMapper, CourseMapper courseMapper) {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.instituteMapper = instituteMapper;
        this.majorMapper = majorMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.courseMapper = courseMapper;
    }

    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }

    public Student getStudentByIdentity(String identity) {
        return studentMapper.findStudentByIdentity(identity);
    }

    public Student getStudentById(Integer id) {
        return studentMapper.findStudentById(id);
    }

    public Student getStudentByStunum(String stunum) {
        return studentMapper.findStudentByStunum(stunum);
    }

    public List<Course> getCoursesByStudent(Student student) {
        List<StuCourse> stuCourses = stuCourseMapper.findAllByStudentid(student.getId());
        List<Course> courses = new ArrayList<>();
        for (StuCourse stuCourse : stuCourses) {
            Course course = courseMapper.findCourseById(stuCourse.getCourseid());
            courses.add(course);
        }
        return courses;
    }

    public Teacher getTeacherByIdentity(String identity) {
        return teacherMapper.findTeacherByIdentity(identity);
    }

    public Institute getInstituteByName(String name) {
        return instituteMapper.findInstituteByName(name);
    }

    public Major getMajorByName(String name) {
        return majorMapper.findMajorByName(name);
    }

    public void createStudent(Student student) {
        studentMapper.save(student);
    }

    public void deleteStudent(Student student) {
        studentMapper.delete(student);
    }

    public void updateStudent(Student student) {
        studentMapper.save(student);
    }
}
