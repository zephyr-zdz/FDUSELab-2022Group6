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

import static java.lang.Integer.parseInt;

@Component("AdminTeacherCourseManager")
public class TeacherCourseManager {
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final CourseMapper courseMapper;
    private final ScheduleMapper scheduleMapper;
    private final CalendarMapper calendarMapper;
    private final TeacherMapper teacherMapper;
    private final ClassroomMapper classroomMapper;
    private final CourseTemplateMapper courseTemplateMapper;
    private final StuCourseMapper stuCourseMapper;
    private final ClassAdapter classAdapter;
    private final CourseAndMajorMapper courseAndMajorMapper;
    private final StudentApplicationMapper studentApplicationMapper;

    @Autowired
    TeacherCourseManager(TeacherApplicationMapper teacherApplicationMapper, CourseMapper courseMapper,
                         ScheduleMapper scheduleMapper, CalendarMapper calendarMapper,
                         TeacherMapper teacherMapper, ClassroomMapper classroomMapper, CourseTemplateMapper courseTemplateMapper, StuCourseMapper stuCourseMapper, ClassAdapter classAdapter, CourseAndMajorMapper courseAndMajorMapper, StudentApplicationMapper studentApplicationMapper){
        this.teacherApplicationMapper = teacherApplicationMapper;
        this.courseMapper=courseMapper;
        this.scheduleMapper=scheduleMapper;
        this.calendarMapper=calendarMapper;
        this.teacherMapper=teacherMapper;
        this.classroomMapper=classroomMapper;
        this.courseTemplateMapper = courseTemplateMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.classAdapter = classAdapter;
        this.courseAndMajorMapper = courseAndMajorMapper;
        this.studentApplicationMapper = studentApplicationMapper;
    }

    public Integer findCalendarIdByDayAndNumber(String day, String number){
        Calendar calendar = calendarMapper.findCalendarByDayAndNumber(day,number);
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
            TeacherCourseApplicationVO teacherCourseApplicationVO = classAdapter.fromTeacherCourseApplication2TeacherCourseApplicationVO(teacherCourseApplication);
            teacherCourseApplicationVOList.add(teacherCourseApplicationVO);
        }

        return teacherCourseApplicationVOList;
    }

    public List<TeacherCourseApplicationVO> findAllPendingCourseApplication() {
        List<TeacherCourseApplication> teacherCourseApplicationList = teacherApplicationMapper.findAllByResult("pending");
        List<TeacherCourseApplicationVO> teacherCourseApplicationVOList = new ArrayList<>();
        for (TeacherCourseApplication teacherCourseApplication : teacherCourseApplicationList) {
            TeacherCourseApplicationVO teacherCourseApplicationVO = classAdapter.fromTeacherCourseApplication2TeacherCourseApplicationVO(teacherCourseApplication);
            teacherCourseApplicationVOList.add(teacherCourseApplicationVO);
        }

        return teacherCourseApplicationVOList;
    }

    public void delete(Course course){
        courseMapper.deleteById(course.getId());
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

    public CourseTemplate getCourseTemplateById(Integer coursetemplateid) {
        return courseTemplateMapper.findCourseTemplateById(coursetemplateid);
    }

    public void deleteStuCourseByCourseid(Integer pre_courseId) {
        stuCourseMapper.deleteStuCoursesByCourseid(pre_courseId);
    }

    public void deleteCourseAndMajorByCourseid(Integer pre_courseId) {
        courseAndMajorMapper.deleteAllByCourseid(pre_courseId);
    }

    public void rejectStudentApplicationByCourseid(Integer pre_courseId) {
        List<StudentCourseApplication> to_be_rejected = studentApplicationMapper.findAllByCourseid(pre_courseId);
        for(StudentCourseApplication object : to_be_rejected){
            object.setResult("reject");
            studentApplicationMapper.save(object);
        }
    }

    public void rejectTeacherCourseApplicationByPrecourseid(Integer precourseid) {
        List<TeacherCourseApplication> to_be_rejected = teacherApplicationMapper.findAllByPrecourseid(precourseid);
        for(TeacherCourseApplication object : to_be_rejected){
            object.setResult("reject");
            teacherApplicationMapper.save(object);
        }
    }

    public void addCourseAndMajor(Integer id, String majoridlist) {
        for(String segment : majoridlist.split(",")){
            CourseAndMajor courseAndMajor = new CourseAndMajor();
            courseAndMajor.setCourseid(id);
            courseAndMajor.setMajorid(parseInt(segment));
            courseAndMajorMapper.save(courseAndMajor);
        }
    }
}
