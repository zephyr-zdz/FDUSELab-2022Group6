package com.example.selab4.mapper;

import com.example.selab4.model.entity.StudentCourseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentApplicationMapper extends JpaRepository<StudentCourseApplication, Integer> {

    List<StudentCourseApplication> findAllByStudentid(Integer studentid);

    List<StudentCourseApplication> findAllByCourseid(Integer pre_courseId);
}
