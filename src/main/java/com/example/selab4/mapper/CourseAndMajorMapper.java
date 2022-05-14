package com.example.selab4.mapper;

import com.example.selab4.model.entity.CourseAndMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAndMajorMapper extends JpaRepository<CourseAndMajor, Integer> {

    List<CourseAndMajor> findCourseAndMajorListByCourseid(Integer courseid);
}
