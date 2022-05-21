package com.example.selab4.service.student;

import com.example.selab4.manager.student.ApplicationManager;
import com.example.selab4.manager.student.CourseManager;
import com.example.selab4.model.checker.StudentCourseChecker;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentApplicationService")
public class ApplicationService {
    private final ApplicationManager manager;
    private final StudentCourseChecker studentCourseChecker;

    @Autowired
    public ApplicationService(ApplicationManager applicationManager, CourseManager courseManager, StudentCourseChecker studentCourseChecker) {
        this.manager = applicationManager;
        this.studentCourseChecker = studentCourseChecker;
    }

    public Response<String> submit(StudentCourseApplication studentCourseApplication) {
        if(!studentCourseChecker.check_apply(studentCourseApplication)){
            return new Response<>(Response.FAIL,"提交失败","发生逻辑错误");
        }
        else {
            studentCourseApplication.setResult("pending");
            manager.save(studentCourseApplication);
            return new Response<>(Response.SUCCESS,"成功","申请已上传");
        }
    }

    public Response<List<StudentCourseApplicationVO>> getApplicationsByStudentid(Integer studentid) {
        return new Response<>(Response.SUCCESS, "成功", manager.getCourseApplicationsByStudentid(studentid));
    }
}
