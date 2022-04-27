package com.example.selab3.controller.teacher;

import com.example.selab3.model.vo.CourseVO;
import com.example.selab3.service.teacher.CourseService;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("TeacherCourseController")
@RequestMapping("/teacher/course")
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    // 教师查看本专业所有课程
    @GetMapping
    public Response<List<CourseVO>> getAllByMajor(@RequestParam("major") String majorName) {
        return service.getAllByMajor(majorName);
    }

    // 教师查看自己的所有课程
    @GetMapping("/jobnum")
    public Response<List<CourseVO>> getAllByJobnum(@RequestParam("jobnum") String jobnum) {
        return service.getAllByJobnum(jobnum);
    }


}
