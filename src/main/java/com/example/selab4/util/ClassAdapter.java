package com.example.selab4.util;


import com.example.selab4.mapper.*;
import com.example.selab4.model.entity.Calendar;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO 进行类型转换，但是对于所有可能出现空指针的情形没有处理
@Component("ClassAdapter")
public class ClassAdapter {
    private final AdminMapper adminMapper;
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final CalendarMapper calendarMapper;
    private final ClassroomMapper classroomMapper;
    private final CourseMapper courseMapper;
    private final InstituteMapper instituteMapper;
    private final MajorMapper majorMapper;
    private final ScheduleMapper scheduleMapper;
    private final StuCourseMapper stuCourseMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final TimeMapper timeMapper;

    @Autowired
    public ClassAdapter(AdminMapper adminMapper, TeacherApplicationMapper teacherApplicationMapper, CalendarMapper calendarMapper, ClassroomMapper classroomMapper, CourseMapper courseMapper, InstituteMapper instituteMapper, MajorMapper majorMapper, ScheduleMapper scheduleMapper, StuCourseMapper stuCourseMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, TimeMapper timeMapper) {
        this.adminMapper = adminMapper;
        this.teacherApplicationMapper = teacherApplicationMapper;
        this.calendarMapper = calendarMapper;
        this.classroomMapper = classroomMapper;
        this.courseMapper = courseMapper;
        this.instituteMapper = instituteMapper;
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.timeMapper = timeMapper;
    }

    public TeacherCourseApplicationVO fromCourseApplication2CourseApplicationVO(TeacherCourseApplication teacherCourseApplication) {
        if (teacherCourseApplication == null) {
            return null;
        }
        TeacherCourseApplicationVO teacherCourseApplicationVO = new TeacherCourseApplicationVO();
        teacherCourseApplicationVO.setTeacherCourseApplication(teacherCourseApplication);
        teacherCourseApplicationVO.setTeacher(teacherMapper.findTeacherById(teacherCourseApplication.getTeacherid()));
        teacherCourseApplicationVO.setMajor(majorMapper.findMajorByName(teacherCourseApplicationVO.getTeacher().getMajor()));
        teacherCourseApplicationVO.setInstitute(instituteMapper.findInstituteByName(teacherCourseApplicationVO.getTeacher().getInstitute()));
        teacherCourseApplicationVO.setClassroom(classroomMapper.findClassroomById(teacherCourseApplication.getClassroomid()));
        return teacherCourseApplicationVO;
    }

    public CourseVO fromCourse2CourseVO(Course course) {
        if (course == null) {
            return null;
        }
        CourseVO courseVO = new CourseVO();
        courseVO.setCourse(course);
        courseVO.setScheduleList(scheduleMapper.findSchedulesByCourseid(course.getId()));
        courseVO.setClassroom(classroomMapper.findClassroomById(courseVO.getScheduleList().get(0).getClassroomid()));
        courseVO.setTeacher(teacherMapper.findTeacherById(courseVO.getScheduleList().get(0).getTeacherid()));
        courseVO.setInstituteOfTeacher(instituteMapper.findInstituteByName(courseVO.getTeacher().getInstitute()));
        courseVO.setMajorOfTeacher(majorMapper.findMajorByName(courseVO.getTeacher().getMajor()));

        List<Calendar> calendarList = new ArrayList<>();
        for (Schedule schedule : courseVO.getScheduleList()) {
            Calendar calendar = calendarMapper.findCalendarById(schedule.getCalendarid());
            calendarList.add(calendar);
        }
        courseVO.setCalendarList(calendarList);

        return courseVO;
    }
}
