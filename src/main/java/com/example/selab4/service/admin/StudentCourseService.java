package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.StudentCourseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminStudentCourseService")
public class StudentCourseService {
    private final StudentCourseManager studentCourseManager;

    @Autowired
    public StudentCourseService(StudentCourseManager studentCourseManager) {
        this.studentCourseManager = studentCourseManager;
    }


}
