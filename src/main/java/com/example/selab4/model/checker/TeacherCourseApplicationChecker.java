package com.example.selab4.model.checker;

import com.example.selab4.mapper.TeacherApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherCourseApplicationChecker {
    private final TeacherApplicationMapper teacherApplicationMapper;

    @Autowired
    public TeacherCourseApplicationChecker(TeacherApplicationMapper teacherApplicationMapper) {
        this.teacherApplicationMapper = teacherApplicationMapper;
    }


}
