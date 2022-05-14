package com.example.selab4.controller.student;

import com.example.selab4.model.entity.Course;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.service.student.CourseService;
import com.example.selab4.util.Response;
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
    public Response<List<CourseVO>> getAllByStunum(@RequestParam("stunum") String stunum) {
        return service.getAllByStunum(stunum);
    }

    // 学生选课
    @PostMapping
    public Response<String> choose(@RequestParam("stunum") String stunum, @RequestParam("coursenum") String coursenum) {
        return service.choose(stunum, coursenum);
    }

    // 学生退课
    @DeleteMapping
    public Response<String> delete(@RequestParam("stunum") String stunum, @RequestParam("coursenum") String coursenum) {
        return service.delete(stunum, coursenum);
    }
}
