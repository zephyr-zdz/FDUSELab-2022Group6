package com.example.selab4.controller.admin;

import com.example.selab4.model.vo.SemesterVO;
import com.example.selab4.service.admin.CourseService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AdminCourseController")
@RequestMapping("/admin/course")
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping("/semester")
    public Response<List<SemesterVO>> getAllSemester() {
        return service.getAllSemester();
    }
}
