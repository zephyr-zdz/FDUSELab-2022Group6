package com.example.selab3.mapper;

import com.example.selab3.model.entity.StuCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuCourseMapper extends JpaRepository<StuCourse, Integer> {
    List<StuCourse> findAllByStudentid(Integer studentid);
}
