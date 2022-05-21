package com.example.selab4.mapper;
import com.example.selab4.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

    List<Course> findCourseBySemester(String semester);
    List<Course> findAllByCoursetemplateid(Integer id);
}
