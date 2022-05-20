package com.example.selab4.manager.admin;


import com.example.selab4.mapper.CourseTemplateMapper;
import com.example.selab4.model.entity.CourseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AdminCourseTemplateManager")
public class CourseTemplateManager {
    private final CourseTemplateMapper courseTemplateMapper;

    @Autowired
    public CourseTemplateManager(CourseTemplateMapper courseTemplateMapper) {
        this.courseTemplateMapper = courseTemplateMapper;
    }

    public List<CourseTemplate> getAllCourseTemplates() {
        return courseTemplateMapper.findAll();
    }

    public void deleteCourseTemplate(CourseTemplate courseTemplate) {
        courseTemplateMapper.delete(courseTemplate);
    }

    public void createCourseTemplate(CourseTemplate courseTemplate) {
        courseTemplateMapper.save(courseTemplate);
    }

    public CourseTemplate getCourseTemplateById(Integer id) {
        return courseTemplateMapper.findCourseTemplateById(id);
    }

    public void updateCourseTemplate(CourseTemplate courseTemplate) {
        courseTemplateMapper.save(courseTemplate);
    }
}
