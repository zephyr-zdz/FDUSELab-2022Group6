package com.example.selab4.service.student;

import com.example.selab4.manager.student.ApplicationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StudentApplicationService")
public class ApplicationService {
    private final ApplicationManager applicationManager;

    @Autowired
    public ApplicationService(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }


}
