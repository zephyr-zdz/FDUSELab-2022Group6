package com.example.selab4.model.checker;

import com.example.selab4.mapper.AdminMapper;
import com.example.selab4.mapper.CourseMapper;
import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.Student;
import com.example.selab4.model.entity.StudentCourseApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.Integer.parseInt;

@Component
public class StudentCourseChecker {
    private final CourseMapper courseMapper;
    private final AdminMapper adminMapper;
    private final ClassAdapter classAdapter;
    private final StudentMapper studentMapper;
    private final ScheduleChecker scheduleChecker;

    @Autowired
    public StudentCourseChecker(CourseMapper courseMapper, AdminMapper adminMapper, ClassAdapter classAdapter, StudentMapper studentMapper, ScheduleChecker scheduleChecker) {
        this.courseMapper = courseMapper;
        this.adminMapper = adminMapper;
        this.classAdapter = classAdapter;
        this.studentMapper = studentMapper;
        this.scheduleChecker = scheduleChecker;
    }


    public boolean check_apply(StudentCourseApplication studentCourseApplication) {
        Course course =  courseMapper.findCourseById(studentCourseApplication.getCourseid());
        boolean full = (Objects.equals(course.getCapacity(), course.getCurrentcount()));
        return full && check_approve(studentCourseApplication);
    }

    public boolean check_approve(StudentCourseApplication studentCourseApplication) {
        Course course =  courseMapper.findCourseById(studentCourseApplication.getCourseid());
        if(course == null) return false;
        Administrator administrator = adminMapper.findAdministratorById(1);
        String current_semester = administrator.getSemester();
        String application_semester = courseMapper.findCourseById(studentCourseApplication.getCourseid()).getSemester();
        int space_capacity = parseInt(classAdapter.fromCourse2CourseVO(course).getClassroom().getCapacity());
        Student student = studentMapper.findStudentById(studentCourseApplication.getStudentid());
        boolean schedule_space = scheduleChecker.checkSchedule(student,course);
        boolean timing = administrator.getChoosecourse2().equals("on");
        boolean enough_space = parseInt(course.getCurrentcount()) < space_capacity;
        boolean current_course = application_semester.equals(current_semester);
        return schedule_space && timing && enough_space && current_course;
    }

}
