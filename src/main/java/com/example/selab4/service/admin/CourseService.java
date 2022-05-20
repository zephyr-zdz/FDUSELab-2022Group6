package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.CourseManager;
import com.example.selab4.model.vo.SemesterVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminCourseService")
public class CourseService {
    private final CourseManager manager;

    @Autowired
    public CourseService(CourseManager manager) {
        this.manager = manager;
    }


    public Response<List<SemesterVO>> getAllSemester() {
        return new Response<>(Response.SUCCESS, "返回所有有课程的学期成功", manager.getAllSemester());
    }
}
