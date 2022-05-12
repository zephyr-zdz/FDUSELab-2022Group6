package com.example.selab4.controller.teacher;

import com.example.selab4.model.entity.CourseApplication;
import com.example.selab4.service.teacher.ApplicationService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("TeacherApplicationController")
@RequestMapping("/teacher/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    ApplicationController(ApplicationService applicationService){
        this.applicationService=applicationService;
    }

    @PostMapping("/apply")
    public Response<String> apply(@RequestBody CourseApplication courseApplication){
        return applicationService.upload(courseApplication);
    }

    @GetMapping("/all")
    public Response<List<CourseApplication>> getMyApplication(String JobNum){
        return applicationService.showMyApplication(JobNum);
    }
}
