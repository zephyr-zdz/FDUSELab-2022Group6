package com.example.selab3.service.student;

import com.example.selab3.manager.student.CourseManager;
import com.example.selab3.model.entity.*;
import com.example.selab3.model.vo.CourseVO;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentCourseService")
public class CourseService {
    private final CourseManager manager;

    @Autowired
    public CourseService(CourseManager manager) {
        this.manager = manager;
    }

    public Response<List<CourseVO>> getAllByMajor(String majorName) {
        Major major = manager.findMajorByName(majorName);
        if (major == null) {
            return new Response<>(Response.FAIL, "专业: " + majorName + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据专业查找课程成功", manager.findCoursesByMajor(major));
    }

    // TODO
    public Response<Course> choose(String courseid) {
        return null;
    }

    public Response<String> isValid() {
        Administrator administrator = manager.findAdminById(1);
        return new Response<>(Response.SUCCESS,"success", administrator.getSelectcoursefunction());
    }

    public Response<List<CourseVO>> getAllByStunum(String stunum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据学号查找课程成功", manager.findCoursesByStudent(student));
    }
}
