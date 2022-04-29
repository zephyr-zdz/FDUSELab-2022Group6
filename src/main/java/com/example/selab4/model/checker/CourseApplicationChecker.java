package com.example.selab4.model.checker;

import com.example.selab4.mapper.ApplicationMapper;
import com.example.selab4.model.entity.CourseApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CourseApplicationChecker {
    private final ApplicationMapper applicationMapper;

    @Autowired
    public CourseApplicationChecker(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }


    public boolean infoComplete(CourseApplication courseApplication) {
        Field[] fields = courseApplication.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(courseApplication);
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
