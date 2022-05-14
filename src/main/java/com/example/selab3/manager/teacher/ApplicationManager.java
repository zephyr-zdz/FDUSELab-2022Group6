package com.example.selab3.manager.teacher;
import com.example.selab3.mapper.*;
import com.example.selab3.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("TeacherApplicationManager")
public class ApplicationManager {
    private final ScheduleMapper scheduleMapper;
    private final CalendarMapper calendarMapper;
    private final ApplicationMapper applicationMapper;
    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;
    private final ClassroomMapper classroomMapper;

    @Autowired
    ApplicationManager(ScheduleMapper scheduleMapper, CalendarMapper calendarMapper,
                       ApplicationMapper applicationMapper, CourseMapper courseMapper,
                       TeacherMapper teacherMapper,ClassroomMapper classroomMapper){
        this.scheduleMapper=scheduleMapper;
        this.calendarMapper=calendarMapper;
        this.applicationMapper=applicationMapper;
        this.courseMapper=courseMapper;
        this.teacherMapper=teacherMapper;
        this.classroomMapper=classroomMapper;
    }

    public Integer getCalendarIdByDayAndNumber(String day, String number){
        Calendar calendar=calendarMapper.findCalendarByDayAndNumber(day,number);
        return calendar.getId();
    }

    public Schedule findScheduleByCalendarIdAndClassroomId(Integer i, Integer classroomId) {
        return scheduleMapper.findScheduleByCalendaridAndClassroomid(i,classroomId);
    }

    public Schedule findScheduleByCalendarIdAndTeacherId(Integer i, Integer teacherId) {
        return scheduleMapper.findScheduleByCalendaridAndTeacherid(i,teacherId);
    }

    public boolean scheduleExistByCalendarIdAndClassroomId(Integer cal_id,Integer class_id){
        Schedule schedule=findScheduleByCalendarIdAndClassroomId(cal_id,class_id);
        return schedule != null;
    }

    public boolean scheduleExistByCalendarIdAndTeacherId(Integer cal_id,Integer teacher_Id){
        Schedule schedule=findScheduleByCalendarIdAndTeacherId(cal_id,teacher_Id);
        return schedule !=null;
    }

    public void save(CourseApplication courseApplication) {
        applicationMapper.save(courseApplication);
    }

    public List<CourseApplication> getCourseApplicationByJobNum(String JobNum){
        Teacher teacher=teacherMapper.findTeacherByJobnum(JobNum);
        return applicationMapper.findAllByTeacherid(teacher.getId());
    }

    public Course findCourseByCourseNum(String courseNum) {
        return courseMapper.findCourseByCoursenum(courseNum);
    }

    public Course findCourseByApplication(CourseApplication courseApplication){
        return findCourseByCourseNum(courseApplication.getCoursenum());
    }

    public Course findCourseById(Integer pre_courseId) {
        return courseMapper.findCourseById(pre_courseId);
    }

    public List<Schedule> deleteSchedulesByCourseId(Integer pre_courseId) {
        List<Schedule> schedules=scheduleMapper.findSchedulesByCourseid(pre_courseId);
        scheduleMapper.deleteSchedulesByCourseid(pre_courseId);
        return schedules;
    }

    public void save(List<Schedule> schedules) {
        scheduleMapper.saveAll(schedules);
    }

    public String findClassroomCapacityById(Integer classroomId) {
        return classroomMapper.findClassroomById(classroomId).getCapacity();
    }

    public Classroom findClassroomById(Integer id) {
        return classroomMapper.findClassroomById(id);
    }
}
