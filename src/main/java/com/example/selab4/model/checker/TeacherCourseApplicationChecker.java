package com.example.selab4.model.checker;

import com.example.selab4.mapper.TeacherApplicationMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Calendar;
import com.example.selab4.model.entity.Classroom;
import com.example.selab4.model.entity.Teacher;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class TeacherCourseApplicationChecker {

    @Autowired
    public TeacherCourseApplicationChecker(TeacherApplicationMapper teacherApplicationMapper, ClassAdapter classAdapter) {
    }
}
