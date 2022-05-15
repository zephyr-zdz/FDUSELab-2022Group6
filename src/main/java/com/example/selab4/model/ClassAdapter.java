package com.example.selab4.model;

import com.example.selab4.mapper.*;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.CourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ClassAdapter")
public class ClassAdapter {
    private final AdminMapper adminMapper;
    private final ApplicationMapper applicationMapper;
    private final CalendarMapper calendarMapper;
    private final ClassroomMapper classroomMapper;
    private final CourseMapper courseMapper;
    private final CourseTemplateMapper courseTemplateMapper;
    private final InstituteMapper instituteMapper;
    private final MajorMapper majorMapper;
    private final ScheduleMapper scheduleMapper;
    private final StuCourseMapper stuCourseMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final TimeMapper timeMapper;
    private final CourseAndMajorMapper courseAndMajorMapper;

    @Autowired
    public ClassAdapter(AdminMapper adminMapper, ApplicationMapper applicationMapper, CalendarMapper calendarMapper, ClassroomMapper classroomMapper, CourseMapper courseMapper, CourseTemplateMapper courseTemplateMapper, InstituteMapper instituteMapper, MajorMapper majorMapper, ScheduleMapper scheduleMapper, StuCourseMapper stuCourseMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, TimeMapper timeMapper, CourseAndMajorMapper courseAndMajorMapper) {
        this.adminMapper = adminMapper;
        this.applicationMapper = applicationMapper;
        this.calendarMapper = calendarMapper;
        this.classroomMapper = classroomMapper;
        this.courseMapper = courseMapper;
        this.courseTemplateMapper = courseTemplateMapper;
        this.instituteMapper = instituteMapper;
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.timeMapper = timeMapper;
        this.courseAndMajorMapper = courseAndMajorMapper;
    }

    public CourseApplicationVO fromCourseApplication2CourseApplicationVO(CourseApplication courseApplication) {
        CourseApplicationVO courseApplicationVO = new CourseApplicationVO();
        courseApplicationVO.setCourseApplication(courseApplication);
        courseApplicationVO.setTeacher(teacherMapper.findTeacherById(courseApplication.getTeacherid()));
        courseApplicationVO.setMajor(majorMapper.findMajorByName(courseApplicationVO.getTeacher().getMajor()));
        courseApplicationVO.setInstitute(instituteMapper.findInstituteByName(courseApplicationVO.getTeacher().getInstitute()));
        courseApplicationVO.setClassroom(classroomMapper.findClassroomById(courseApplication.getClassroomid()));
        return courseApplicationVO;
    }

    public CourseVO fromCourse2CourseVO(Course course) {
        CourseVO courseVO = new CourseVO();
        courseVO.setCourse(course);
        courseVO.setCourseTemplate(courseTemplateMapper.findCourseTemplateById(course.getCoursetemplateid()));
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

        List<Major> majorListOfCourse = new ArrayList<>();
        for (CourseAndMajor courseAndMajor : courseAndMajorMapper.findCourseAndMajorListByCourseid(course.getId())) {
            Major major = majorMapper.findMajorById(courseAndMajor.getMajorid());
            majorListOfCourse.add(major);
        }
        courseVO.setMajorListOfCourse(majorListOfCourse);

        return courseVO;
    }
}
