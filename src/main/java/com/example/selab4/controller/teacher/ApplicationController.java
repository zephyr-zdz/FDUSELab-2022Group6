package com.example.selab4.controller.teacher;

import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.service.teacher.ApplicationService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("TeacherApplicationController")
@RequestMapping("/teacher/application")
public class ApplicationController {
    private final ApplicationService service;

    @Autowired
    ApplicationController(ApplicationService applicationService){
        this.service =applicationService;
    }

    @PostMapping("/apply")
    public Response<String> apply(@RequestBody TeacherCourseApplication teacherCourseApplication){
        return service.upload(teacherCourseApplication);
    }

    @GetMapping("/all")
    public Response<List<TeacherCourseApplicationVO>> getApplicationsByTeacherid(@RequestParam("teacherid") Integer teacherid){
        return service.getApplicationsByTeacherid(teacherid);
    }
}
