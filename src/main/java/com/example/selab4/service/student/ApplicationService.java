package com.example.selab4.service.student;

import com.example.selab4.manager.student.ApplicationManager;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StudentApplicationService")
public class ApplicationService {
    private final ApplicationManager applicationManager;

    @Autowired
    public ApplicationService(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    public Response<String> submit(StudentCourseApplication studentCourseApplication) {
        if(!applicationManager.check(studentCourseApplication)){
            return new Response<>(Response.FAIL,"err","conflict");
        }
        else {
            applicationManager.save(studentCourseApplication);
            return new Response<>(Response.SUCCESS,"success","application uploaded");
        }
    }
}
