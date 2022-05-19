package com.example.selab4.controller.student;

import com.example.selab4.mapper.StudentApplicationMapper;
import com.example.selab4.model.entity.Student;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.service.student.ApplicationService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.coyote.http11.Constants.a;

@RestController("StudentApplicationController")
@RequestMapping("/student/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/submit")
    public Response<String> submitApplication(StudentCourseApplication studentCourseApplication){
        return applicationService.submit(studentCourseApplication);
    }

}
