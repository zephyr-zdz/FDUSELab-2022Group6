package com.example.selab4.service.student;

import com.example.selab4.manager.student.ApplicationManager;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentApplicationService")
public class ApplicationService {
    private final ApplicationManager manager;

    @Autowired
    public ApplicationService(ApplicationManager applicationManager) {
        this.manager = applicationManager;
    }

    public Response<String> submit(StudentCourseApplication studentCourseApplication) {
        if(!manager.check(studentCourseApplication)){
            return new Response<>(Response.FAIL,"err","conflict");
        }
        else {
            manager.save(studentCourseApplication);
            return new Response<>(Response.SUCCESS,"success","application uploaded");
        }
    }

    public Response<List<StudentCourseApplicationVO>> getApplicationsByStudentid(Integer studentid) {
        return new Response<>(Response.SUCCESS, "success", manager.getCourseApplicationsByStudentid(studentid));
    }
}
