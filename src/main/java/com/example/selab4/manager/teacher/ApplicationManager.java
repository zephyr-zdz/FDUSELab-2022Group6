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

    public void save(TeacherCourseApplication teacherCourseApplication) {
        teacherApplicationMapper.save(teacherCourseApplication);
    }

    public List<TeacherCourseApplication> getCourseApplicationByJobNum(String JobNum){
        Teacher teacher=teacherMapper.findTeacherByJobnum(JobNum);
        return teacherApplicationMapper.findAllByTeacherid(teacher.getId());
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

    public boolean changeIspublic(TeacherCourseApplication teacherCourseApplication) {
        Course course = courseMapper.findCourseById(teacherCourseApplication.getPre_courseId());
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
