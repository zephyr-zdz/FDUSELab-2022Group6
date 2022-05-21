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
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    public TeacherCourseApplicationChecker(TeacherApplicationMapper teacherApplicationMapper, ClassAdapter classAdapter) {
        this.teacherApplicationMapper = teacherApplicationMapper;
        this.classAdapter = classAdapter;
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
