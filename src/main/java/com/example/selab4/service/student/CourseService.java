package com.example.selab4.service.student;

import com.example.selab4.manager.student.CourseManager;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.util.Response;
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

    public Response<String> choose(String stunum, String coursenum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        Course course = manager.findCourseByCoursenum(coursenum);
        if (course == null) {
            return new Response<>(Response.FAIL, "课程编号: " + coursenum + "不存在", null);
        }

        // 1、课程人数上限
        if (!manager.checkCapacity(course)) {
            return new Response<>(Response.FAIL, "课程编号: " + coursenum + "的课程人数已满", null);
        }

        // 2、学生的时间日程是否冲突
        if (!manager.checkSchedule(student, course)) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "的学生上课时间发生冲突", null);
        }

        manager.choose(student, course);
        return new Response<>(Response.SUCCESS, "学生选课成功", null);
    }

    public Response<String> delete(String stunum, String coursenum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        Course course = manager.findCourseByCoursenum(coursenum);
        if (course == null) {
            return new Response<>(Response.FAIL, "课程编号: " + coursenum + "不存在", null);
        }

        // 学生是否选了该课
        if (!manager.checkStuCourse(student, course)) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "的学生没有选过课程编号: " + coursenum + "的课程", null);
        }

        manager.delete(student, course);
        return new Response<>(Response.SUCCESS, "学生退课成功", null);
    }
}
