package com.example.selab4.controller.student;

import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.service.student.ApplicationService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentApplicationController")
@RequestMapping("/student/application")
public class ApplicationController {
    private final ApplicationService service;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.service = applicationService;
    }

    @PostMapping("/submit")
    public Response<String> submitApplication(@RequestBody StudentCourseApplication studentCourseApplication){
        return service.submit(studentCourseApplication);
    }

    @GetMapping("/all")
    public Response<List<StudentCourseApplicationVO>> getApplicationsByStudentid(@RequestParam("studentid") Integer studentid) {
        return service.getApplicationsByStudentid(studentid);
    }

}
