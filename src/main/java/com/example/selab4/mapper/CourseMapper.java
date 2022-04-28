package com.example.selab4.mapper;
import com.example.selab4.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper extends JpaRepository<Course,Integer> {
    Course findCourseByCoursenum(String courseNum);
    Course findCourseById(Integer id);
}
