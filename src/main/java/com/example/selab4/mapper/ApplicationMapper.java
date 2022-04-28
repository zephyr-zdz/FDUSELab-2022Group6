package com.example.selab4.mapper;
import com.example.selab4.model.entity.CourseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationMapper extends JpaRepository<CourseApplication,Integer> {
    @Query(value = "select * from course_application ",nativeQuery = true)
    List<CourseApplication> findAll();
    List<CourseApplication> findAllByTeacherid(Integer teacherid);
    List<CourseApplication> findAllByResult(String result);
}
