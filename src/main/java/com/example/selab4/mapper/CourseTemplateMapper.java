package com.example.selab4.mapper;

import com.example.selab4.model.entity.CourseTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTemplateMapper extends JpaRepository<CourseTemplate, Integer> {
    CourseTemplate findCourseTemplateById(Integer id);
    CourseTemplate findCourseTemplateByCoursenum(String coursenum);
    CourseTemplate findCourseTemplateByName(String name);
}
