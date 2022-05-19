package com.example.selab4.mapper;

import com.example.selab4.model.entity.StuCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuCourseMapper extends JpaRepository<StuCourse, Integer> {
    Integer countByCourseid(Integer courseid);

    List<StuCourse> findAllByStudentid(Integer studentid);

    List<StuCourse> findStuCoursesByStudentid(Integer studentid);

    void deleteByStudentidAndCourseid(Integer studentid, Integer courseid);

    List<StuCourse> findStuCoursesByStudentidAndCourseid(Integer studentid, Integer courseid);

    List<StuCourse> findAllByStudentidAndStatus(Integer studentid,String status);

    List<StuCourse> findStuCoursesByStudentidAndCourseidAndStatus(Integer studentid, Integer courseid, String status);

    void deleteByStudentidAndCourseidAndStatus(Integer studentid, Integer courseid, String status);

    List<StuCourse> findStuCoursesByStudentidAndStatus(Integer studentid, String status);

    List<StuCourse> findStuCoursesByCourseidAndStatus(Integer courseid, String status);

    List<StuCourse> findStuCoursesByCourseid(Integer courseid);
}
