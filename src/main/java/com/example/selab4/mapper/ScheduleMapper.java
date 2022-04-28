package com.example.selab4.mapper;

import com.example.selab4.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleMapper extends JpaRepository<Schedule,Integer> {
    Schedule findScheduleByCalendaridAndClassroomid(Integer calendar_id,Integer classroom_id);
    Schedule findScheduleByCalendaridAndTeacherid(Integer calendar_id,Integer teacher_id);
    List<Schedule> findSchedulesByCourseid(Integer courseId);
    void deleteSchedulesByCourseid(Integer courseId);
    List<Schedule> findAllByTeacherid(Integer teacherid);
}
