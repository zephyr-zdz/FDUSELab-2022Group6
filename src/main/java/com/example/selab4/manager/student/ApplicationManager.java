package com.example.selab4.manager.student;

import com.example.selab4.mapper.AdminMapper;
import com.example.selab4.mapper.CourseMapper;
import com.example.selab4.mapper.StudentApplicationMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.model.entity.Classroom;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.StudentCourseApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.Integer.parseInt;

@Component("StudentApplicationManager")
public class ApplicationManager {
    private final StudentApplicationMapper studentApplicationMapper;
    private final CourseMapper courseMapper;
    private final ClassAdapter classAdapter;
    private final AdminMapper adminMapper;

    @Autowired
    public ApplicationManager(StudentApplicationMapper studentApplicationMapper, CourseMapper courseMapper, ClassAdapter classAdapter, AdminMapper adminMapper) {
        this.studentApplicationMapper = studentApplicationMapper;
        this.courseMapper = courseMapper;
        this.classAdapter = classAdapter;
        this.adminMapper = adminMapper;
    }


    public boolean check(StudentCourseApplication studentCourseApplication) {
        Course course =  courseMapper.findCourseById(studentCourseApplication.getCourseid());
        Administrator administrator = adminMapper.findAdministratorById(1);
        int space_capacity = parseInt(classAdapter.fromCourse2CourseVO(course).getClassroom().getCapacity());
        boolean full = (Objects.equals(course.getCapacity(), course.getCurrentcount()));
        boolean timing = administrator.getChoosecourse2().equals("on");
        boolean enough_space = parseInt(course.getCurrentcount()) < space_capacity;

        return full && timing && enough_space;
    }

    public void save(StudentCourseApplication studentCourseApplication) {
        studentApplicationMapper.save(studentCourseApplication);
    }
}
