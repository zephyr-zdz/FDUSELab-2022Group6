package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.StudentCourseManager;
import com.example.selab4.model.vo.StudentVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminStudentCourseService")
public class StudentCourseService {
    private final StudentCourseManager manager;

    @Autowired
    public StudentCourseService(StudentCourseManager studentCourseManager) {
        this.manager = studentCourseManager;
    }

    public Response<List<StudentVO>> getStudentListByCourseid(Integer courseid) {
        return new Response<>(Response.SUCCESS, "查找选课学生名单成功", manager.getStudentListByCourseid(courseid));
    }
}
