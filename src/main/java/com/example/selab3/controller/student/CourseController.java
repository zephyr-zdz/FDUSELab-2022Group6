package com.example.selab3.controller.student;

import com.example.selab3.model.entity.Course;
import com.example.selab3.model.vo.CourseVO;
import com.example.selab3.service.student.CourseService;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentCourseController")
@RequestMapping("student/course")
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    // 学生查看选课功能状态
    @GetMapping("/isValid")
    public Response<String> isValid() {
        return service.isValid();
    }

    // 学生查看本专业所有课程
    @GetMapping
    public Response<List<CourseVO>> getAllByMajor(@RequestParam("major") String majorName) {
        return service.getAllByMajor(majorName);
    }

    // 学生查看自己的所有课程
    @GetMapping("/stunum")
    public Response<List<CourseVO>> getAllByJobnum(@RequestParam("stunum") String stunum) {
        return service.getAllByStunum(stunum);
    }

    // 学生选课 TODO
    @PostMapping
    public Response<Course> choose(@RequestParam("courseid") String courseid) {
        return service.choose(courseid);
    }
}
