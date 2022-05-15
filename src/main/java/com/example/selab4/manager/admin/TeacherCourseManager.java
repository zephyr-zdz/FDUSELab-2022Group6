package com.example.selab4.manager.admin;

import com.example.selab4.mapper.*;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AdminTeacherCourseManager")
public class TeacherCourseManager {
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final CourseMapper courseMapper;
    private final ScheduleMapper scheduleMapper;
    private final CalendarMapper calendarMapper;
    private final TeacherMapper teacherMapper;
    private final ClassroomMapper classroomMapper;
    private final CourseTemplateMapper courseTemplateMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    TeacherCourseManager(TeacherApplicationMapper teacherApplicationMapper, CourseMapper courseMapper,
                         ScheduleMapper scheduleMapper, CalendarMapper calendarMapper,
                         TeacherMapper teacherMapper, ClassroomMapper classroomMapper, CourseTemplateMapper courseTemplateMapper, ClassAdapter classAdapter){
        this.teacherApplicationMapper = teacherApplicationMapper;
        this.courseMapper=courseMapper;
        this.scheduleMapper=scheduleMapper;
        this.calendarMapper=calendarMapper;
        this.teacherMapper=teacherMapper;
        this.classroomMapper=classroomMapper;
        this.courseTemplateMapper = courseTemplateMapper;
        this.classAdapter = classAdapter;
    }

    public Integer findCalendarIdByDayAndNumber(String day, String number){
        Calendar calendar=calendarMapper.findCalendarByDayAndNumber(day,number);
        return calendar.getId();
    }

    public String findTeacherJobNumById(Integer Id){
        return teacherMapper.findTeacherById(Id).getJobnum();
    }

    public Integer findTeacherIdByJobNum(String JobNum){
        return teacherMapper.findTeacherByJobnum(JobNum).getId();
    }

    public void save(TeacherCourseApplication teacherCourseApplication){
        teacherApplicationMapper.save(teacherCourseApplication);
    }

    public List<TeacherCourseApplicationVO> findAllCourseApplication(){
        List<TeacherCourseApplication> teacherCourseApplicationList = teacherApplicationMapper.findAll();
        List<TeacherCourseApplicationVO> teacherCourseApplicationVOList = new ArrayList<>();
        for (TeacherCourseApplication teacherCourseApplication : teacherCourseApplicationList) {
            TeacherCourseApplicationVO teacherCourseApplicationVO = classAdapter.fromCourseApplication2CourseApplicationVO(teacherCourseApplication);
            teacherCourseApplicationVOList.add(teacherCourseApplicationVO);
        }

        return teacherCourseApplicationVOList;
    }

    public List<TeacherCourseApplicationVO> findAllPendingCourseApplication() {
        List<TeacherCourseApplication> teacherCourseApplicationList = teacherApplicationMapper.findAllByResult("pending");
        List<TeacherCourseApplicationVO> teacherCourseApplicationVOList = new ArrayList<>();
        for (TeacherCourseApplication teacherCourseApplication : teacherCourseApplicationList) {
            TeacherCourseApplicationVO teacherCourseApplicationVO = classAdapter.fromCourseApplication2CourseApplicationVO(teacherCourseApplication);
            teacherCourseApplicationVOList.add(teacherCourseApplicationVO);
        }

        return teacherCourseApplicationVOList;
    }

    public void delete(Course course){
        courseMapper.delete(course);
    }


    public void save(Course course) {
        courseMapper.save(course);
    }

    public List<CourseVO> findAllCourse(){
        List<Course> courseList = courseMapper.findAll();
        List<CourseVO> courseVOList = new ArrayList<>();
        for (Course course : courseList) {
            CourseVO courseVO = classAdapter.fromCourse2CourseVO(course);
            courseVOList.add(courseVO);
        }

        return courseVOList;
    }

    public List<Schedule> deleteSchedulesByCourseId(Integer courseId){
        List<Schedule> schedules=scheduleMapper.findSchedulesByCourseid(courseId);
        scheduleMapper.deleteSchedulesByCourseid(courseId);
        return schedules;
    }


    public void save(List<Schedule> schedules) {
        scheduleMapper.saveAll(schedules);
    }

    public Schedule findScheduleByCalendarIdAndClassroomId(Integer cal_id, Integer class_id){
        return scheduleMapper.findScheduleByCalendaridAndClassroomid(cal_id,class_id);
    }

    public Schedule findScheduleByCalendarIdAndTeacherId(Integer cal_id, Integer tec_id){
        return scheduleMapper.findScheduleByCalendaridAndTeacherid(cal_id,tec_id);
    }


    public boolean scheduleExistByCalendarIdAndClassroomId(Integer cal_id,Integer class_id){
        Schedule schedule1 = findScheduleByCalendarIdAndClassroomId(cal_id,class_id);
        return schedule1 != null;
    }

    public boolean scheduleExistByCalendarIdAndTeacherId(Integer cal_id,Integer tec_id){
        Schedule schedule1= findScheduleByCalendarIdAndTeacherId(cal_id,tec_id);
        return schedule1 != null;
    }

    public List<Schedule> findSchedulesByCourseId(Integer id) {
        return scheduleMapper.findSchedulesByCourseid(id);
    }

    public Calendar findCalendarById(Integer calendarId) {
        return calendarMapper.findCalendarById(calendarId);
    }

    public String findClassroomNumById(Integer id){
        return classroomMapper.findClassroomById(id).getName();
    }

    public Classroom findClassroomById(Integer id) {
        return classroomMapper.findClassroomById(id);
    }

    public Course findCourseByCourseId(Integer course_id) {
        return courseMapper.findCourseById(course_id);
    }

    public CourseTemplate getCourseTemplateByCoursenum(String coursenum) {
        return courseTemplateMapper.findCourseTemplateByCoursenum(coursenum);
    }

    public String findClassroomCapacityById(Integer classroomId) {
        return classroomMapper.findClassroomById(classroomId).getCapacity();
    }
}
