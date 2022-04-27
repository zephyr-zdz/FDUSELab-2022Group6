package com.example.selab3.service.teacher;

import com.example.selab3.manager.teacher.CourseManager;
import com.example.selab3.model.entity.Major;
import com.example.selab3.model.entity.Teacher;
import com.example.selab3.model.vo.CourseVO;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TeacherCourseService")
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

    public Response<List<CourseVO>> getAllByJobnum(String jobnum) {
        Teacher teacher = manager.findTeacherByJobnum(jobnum);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "工号: " + jobnum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据工号查找课程成功", manager.findCoursesByTeacher(teacher));
    }
}
