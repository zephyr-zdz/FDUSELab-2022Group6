package com.example.selab4.model.checker;

import com.example.selab4.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseChecker {
    private final CourseMapper courseMapper;

    @Autowired
    public CourseChecker(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }


}
