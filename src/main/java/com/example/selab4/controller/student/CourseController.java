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
    @GetMapping("/valid")
    public Response<String> isValid() {
        return service.isValid();
    }

    // 学生查看本专业所有可选课程
    @GetMapping("/major")
    public Response<List<CourseVO>> getAllByMajor(@RequestParam("major") String majorName) {
        return service.getAllByMajor(majorName);
    }

    // 学生查看所有已选/已修的课程
    @GetMapping("/stunum")
    public Response<List<CourseVO>> getAllByStunum(@RequestParam("stunum") String stunum) {
        return service.getAllByStunum(stunum);
    }

    // 学生查看所有已选课程
    @GetMapping("/selected")
    public Response<List<CourseVO>> getSelectedByStunum(@RequestParam("stunum") String stunum){
        return service.getSelectedByStunum(stunum);
    }

    // 学生查看所有已修课程
    @GetMapping("/finished")
    public Response<List<CourseVO>> getFinishedByStunum(@RequestParam("stunum") String stunum){
        return service.getFinishedByStunum(stunum);
    }

    // 学生选课（学生看到的课都是能选课的专业）
    @PostMapping("/choose")
    public Response<String> choose(@RequestParam("studentid") Integer studentid, @RequestParam("courseid") Integer courseid) {
        return service.choose(studentid, courseid);
    }

    // 学生退课
    @DeleteMapping("/delete")
    public Response<String> delete(@RequestParam("studentid") Integer studentid, @RequestParam("courseid") Integer courseid) {
        return service.delete(studentid, courseid);
    }
}
