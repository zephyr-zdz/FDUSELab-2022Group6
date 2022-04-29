package com.example.selab4.model.checker;

import com.example.selab4.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherCourseApplicationChecker {
    private final ApplicationMapper applicationMapper;

    @Autowired
    public TeacherCourseApplicationChecker(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }


}
