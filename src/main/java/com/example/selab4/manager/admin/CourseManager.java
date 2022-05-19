package com.example.selab4.manager.admin;

import com.example.selab4.mapper.CourseMapper;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.vo.SemesterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Component("AdminCourseManager")
public class CourseManager {
    private final CourseMapper courseMapper;

    @Autowired
    public CourseManager(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<SemesterVO> getAllSemester() {
        List<Course> courseList = courseMapper.findAll();
        List<SemesterVO> semesterList = new ArrayList<>();
        for (Course course : courseList) {
            SemesterVO semesterVO = new SemesterVO();
            semesterVO.setText(course.getSemester());
            semesterVO.setValue(course.getSemester());
            semesterList.add(semesterVO);
        }

        // 去重
        LinkedHashSet<SemesterVO> hashSet = new LinkedHashSet<>(semesterList);
        semesterList = new ArrayList<>(hashSet);

        return semesterList;
    }
}
