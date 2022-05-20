package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.StudentCourseManager;
import com.example.selab4.model.checker.StudentCourseChecker;
import com.example.selab4.model.entity.StuCourse;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.model.vo.StudentVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminStudentCourseService")
public class StudentCourseService {
    private final StudentCourseManager manager;
    private final StudentCourseChecker studentCourseChecker;


    @Autowired
    public StudentCourseService(StudentCourseManager studentCourseManager, StudentCourseChecker studentCourseChecker) {
        this.manager = studentCourseManager;
        this.studentCourseChecker = studentCourseChecker;
    }

    public Response<List<StudentVO>> getStudentListByCourseid(Integer courseid) {
        return new Response<>(Response.SUCCESS, "查找选课学生名单成功", manager.getStudentListByCourseid(courseid));
    }

    public Response<List<StudentCourseApplicationVO>> getStudentCourseApplicationVOList() {
        return new Response<>(Response.SUCCESS, "查找学生选课申请成功", manager.getStudentCourseApplicationVOList());
    }


    public Response<String> approve(StudentCourseApplication studentCourseApplication, boolean attitude) {
        if(!attitude){
            studentCourseApplication.setResult("reject");
            manager.save(studentCourseApplication);
            return new Response<>(Response.SUCCESS,"审批成功","结果：拒绝");
        }
        if(!studentCourseChecker.check_approve(studentCourseApplication))
            return new Response<>(Response.FAIL,"发生逻辑错误，审批不能通过","结果：拒绝");

        StuCourse stuCourse = new StuCourse();
        stuCourse.initialize(studentCourseApplication.getCourseid(),studentCourseApplication.getStudentid());
        manager.save(stuCourse);
        studentCourseApplication.setResult("approve");
        manager.save(studentCourseApplication);
        return new Response<>(Response.SUCCESS,"审批成功","结果：通过");
    }
}
