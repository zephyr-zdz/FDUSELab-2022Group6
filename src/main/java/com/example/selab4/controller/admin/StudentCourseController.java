package com.example.selab4.controller.admin;

import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.model.vo.StudentVO;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.service.admin.StudentCourseService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminStudentCourseController")
@RequestMapping("/admin/student-course")
public class StudentCourseController {
    private final StudentCourseService service;

    @Autowired
    public StudentCourseController(StudentCourseService service) {
        this.service = service;
    }

    // 管理员查看某个课程的选课学生名单
    @GetMapping("/student-list")
    public Response<List<StudentVO>> getStudentListByCourseid(@RequestParam("courseid") Integer courseid) {
        return service.getStudentListByCourseid(courseid);
    }

    // 管理员查看所有的学生选课申请
    @GetMapping("/student-application-list")
    public Response<List<StudentCourseApplicationVO>> getStudentCourseApplicationVOList() {
        return service.getStudentCourseApplicationVOList();
    }

    // TODO 管理员审核学生的选课申请
    @PostMapping("/approve")
    public Response<String> approve(@RequestBody StudentCourseApplication studentCourseApplication, @RequestParam(name = "attitude") boolean attitude){
        return service.approve(studentCourseApplication,attitude);
    }


}
