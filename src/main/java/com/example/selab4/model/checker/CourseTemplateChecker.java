package com.example.selab4.model.checker;

import com.example.selab4.mapper.CourseTemplateMapper;
import com.example.selab4.model.entity.CourseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseTemplateChecker {
    private final CourseTemplateMapper courseTemplateMapper;

    @Autowired
    public CourseTemplateChecker(CourseTemplateMapper courseTemplateMapper) {
        this.courseTemplateMapper = courseTemplateMapper;
    }

    public boolean haveId(CourseTemplate courseTemplate) {
        return courseTemplate.getId() != null;
    }

    private boolean haveCoursenum(CourseTemplate courseTemplate) {
        return courseTemplate.getCoursenum() != null;
    }

    private boolean haveName(CourseTemplate courseTemplate) {
        return courseTemplate.getName() != null;
    }

    public boolean exist(CourseTemplate courseTemplate) {
        if (!haveId(courseTemplate)) {
            return false;
        }

        CourseTemplate existedCourseTemplate = courseTemplateMapper.findCourseTemplateById(courseTemplate.getId());
        if (existedCourseTemplate == null) {
            return false;
        }

        return existedCourseTemplate.equals(courseTemplate);
    }

    public boolean existCoursenum(CourseTemplate courseTemplate) {
        if (!haveCoursenum(courseTemplate)) {
            return false;
        }
        CourseTemplate existedCourseTemplate = courseTemplateMapper.findCourseTemplateByCoursenum(courseTemplate.getCoursenum());
        return existedCourseTemplate != null;
    }

    public boolean existOtherCoursenum(CourseTemplate courseTemplate, CourseTemplate oldCourseTemplate) {
        if (!haveCoursenum(courseTemplate)) {
            return false;
        }

        if (courseTemplate.getCoursenum().equals(oldCourseTemplate.getCoursenum())) {
            return false;
        }

        return existCoursenum(courseTemplate);
    }

    public boolean existName(CourseTemplate courseTemplate) {
        if (!haveName(courseTemplate)) {
            return false;
        }
        CourseTemplate existedCourseTemplate = courseTemplateMapper.findCourseTemplateByName(courseTemplate.getName());
        return existedCourseTemplate != null;
    }

    public boolean existOtherName(CourseTemplate courseTemplate, CourseTemplate oldCourseTemplate) {
        if (!haveName(courseTemplate)) {
            return false;
        }

        if (courseTemplate.getName().equals(oldCourseTemplate.getName())) {
            return false;
        }

        return existName(courseTemplate);
    }

    public boolean existId(CourseTemplate courseTemplate) {
        if (!haveId(courseTemplate)) {
            return false;
        }
        CourseTemplate existedCourseTemplate = courseTemplateMapper.findCourseTemplateById(courseTemplate.getId());
        return existedCourseTemplate != null;
    }
}
