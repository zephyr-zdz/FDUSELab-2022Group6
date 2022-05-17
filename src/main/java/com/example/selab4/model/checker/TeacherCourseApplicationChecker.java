package com.example.selab4.model.checker;

import com.example.selab4.mapper.TeacherApplicationMapper;
import com.example.selab4.model.entity.TeacherCourseApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class TeacherCourseApplicationChecker {
    private final TeacherApplicationMapper teacherApplicationMapper;

    @Autowired
    public TeacherCourseApplicationChecker(TeacherApplicationMapper teacherApplicationMapper) {
        this.teacherApplicationMapper = teacherApplicationMapper;
    }


    public boolean infoComplete(TeacherCourseApplication teacherCourseApplication) {
        Field[] fields = teacherCourseApplication.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(teacherCourseApplication);
                if (value != null) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
