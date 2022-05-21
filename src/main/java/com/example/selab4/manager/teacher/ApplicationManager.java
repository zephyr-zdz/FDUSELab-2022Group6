package com.example.selab4.manager.teacher;
import com.example.selab4.mapper.*;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("TeacherApplicationManager")
public class ApplicationManager {
    private final ScheduleMapper scheduleMapper;
    private final CalendarMapper calendarMapper;
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;
    private final ClassroomMapper classroomMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    ApplicationManager(ScheduleMapper scheduleMapper, CalendarMapper calendarMapper,
                       TeacherApplicationMapper teacherApplicationMapper, CourseMapper courseMapper,
                       TeacherMapper teacherMapper, ClassroomMapper classroomMapper, ClassAdapter classAdapter){
        this.scheduleMapper=scheduleMapper;
        this.calendarMapper=calendarMapper;
        this.teacherApplicationMapper = teacherApplicationMapper;
        this.courseMapper=courseMapper;
        this.teacherMapper=teacherMapper;
        this.classroomMapper=classroomMapper;
        this.classAdapter = classAdapter;
    }

    public Integer getCalendarIdByDayAndNumber(String day, String number){
        Calendar calendar=calendarMapper.findCalendarByDayAndNumber(day,number);
        return calendar.getId();
    }

    public List<Schedule> findScheduleListByCalendarIdAndClassroomId(Integer i, Integer classroomId) {
        return scheduleMapper.findScheduleListByCalendaridAndClassroomid(i,classroomId);
    }

    public List<Schedule> findScheduleListByCalendarIdAndTeacherId(Integer i, Integer teacherId) {
        return scheduleMapper.findScheduleListByCalendaridAndTeacherid(i,teacherId);
    }

    public boolean scheduleExistByCalendarIdAndClassroomIdAndSemester(Integer cal_id, Integer class_id, String semester){
        List<Schedule> scheduleList = findScheduleListByCalendarIdAndClassroomId(cal_id,class_id);
        for (Schedule schedule : scheduleList) {
            Course course = courseMapper.findCourseById(schedule.getCourseid());
            if (course.getSemester().equals(semester)) {
                return true;
            }
        }

        return false;
    }

    public boolean scheduleExistByCalendarIdAndTeacherIdAndSemester(Integer cal_id, Integer tec_id, String semester){
        List<Schedule> scheduleList = findScheduleListByCalendarIdAndTeacherId(cal_id,tec_id);
        for (Schedule schedule : scheduleList) {
            Course course = courseMapper.findCourseById(schedule.getCourseid());
            if (course.getSemester().equals(semester)) {
                return true;
            }
        }

        return false;
    }

    public void save(TeacherCourseApplication teacherCourseApplication) {
        teacherApplicationMapper.save(teacherCourseApplication);
    }

    public Course findCourseById(Integer courseid) {
        return courseMapper.findCourseById(courseid);
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

    public boolean changeIspublic(TeacherCourseApplication teacherCourseApplication) {
        Course course = courseMapper.findCourseById(teacherCourseApplication.getPrecourseid());
        return !teacherCourseApplication.getIspublic().equals(course.getIspublic());
    }

    public List<TeacherCourseApplicationVO> getApplicationsByTeacherid(Integer teacherid) {
        List<TeacherCourseApplication> teacherCourseApplicationList = teacherApplicationMapper.findAllByTeacherid(teacherid);
        List<TeacherCourseApplicationVO> teacherCourseApplicationVOList = new ArrayList<>();
        for (TeacherCourseApplication teacherCourseApplication : teacherCourseApplicationList) {
            TeacherCourseApplicationVO teacherCourseApplicationVO = classAdapter.fromTeacherCourseApplication2TeacherCourseApplicationVO(teacherCourseApplication);
            teacherCourseApplicationVOList.add(teacherCourseApplicationVO);
        }

        return teacherCourseApplicationVOList;
    }
}
