package com.example.selab4.controller.admin;

import com.example.selab4.model.vo.StudentVO;
import com.example.selab4.service.admin.StudentCourseService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AdminStudentCourseController")
@RequestMapping("/admin/studentcourse")
public class StudentCourseController {
    private final StudentCourseService service;

    @Autowired
    public StudentCourseController(StudentCourseService service) {
        this.service = service;
    }

    // TODO 管理员查看某个课程的选课学生名单
    public Response<List<StudentVO>> getStudentListByCourseid(@RequestParam("courseid") Integer courseid) {
        return service.getStudentListByCourseid(courseid);
    }

    // TODO 第一轮选课结束瞬间，管理员根据优先级退选超出容量的学生

    // TODO 管理员审核学生的选课申请

}
