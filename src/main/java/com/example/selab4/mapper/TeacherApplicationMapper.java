package com.example.selab4.mapper;
import com.example.selab4.model.entity.TeacherCourseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherApplicationMapper extends JpaRepository<TeacherCourseApplication,Integer> {
    @Query(value = "select * from course_application ",nativeQuery = true)
    List<TeacherCourseApplication> findAll();
    List<TeacherCourseApplication> findAllByTeacherid(Integer teacherid);
    List<TeacherCourseApplication> findAllByResult(String result);
}
