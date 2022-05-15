package com.example.selab4.controller.admin;

import com.example.selab4.service.admin.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminStudentCourseController")
@RequestMapping("/admin/studentcourse")
public class StudentCourseController {
    private final StudentCourseService service;

    @Autowired
    public StudentCourseController(StudentCourseService service) {
        this.service = service;
    }



}
