package com.example.selab4.manager.student;

import com.example.selab4.mapper.*;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component("StudentCourseManager")
public class CourseManager {
    private final MajorMapper majorMapper;
    private final ScheduleMapper scheduleMapper;
    private final CourseMapper courseMapper;
    private final AdminMapper adminMapper;
    private final StudentMapper studentMapper;
    private final StuCourseMapper stuCourseMapper;
    private final ClassAdapter classAdapter;
    private final CourseTemplateMapper courseTemplateMapper;
    private final CourseAndMajorMapper courseAndMajorMapper;

    @Autowired
    public CourseManager(MajorMapper majorMapper, ScheduleMapper scheduleMapper, CourseMapper courseMapper, AdminMapper adminMapper, StudentMapper studentMapper, StuCourseMapper stuCourseMapper, ClassAdapter classAdapter, CourseTemplateMapper courseTemplateMapper, CourseAndMajorMapper courseAndMajorMapper) {
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.courseMapper = courseMapper;
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.classAdapter = classAdapter;
        this.courseTemplateMapper = courseTemplateMapper;
        this.courseAndMajorMapper = courseAndMajorMapper;
    }

    public Major findMajorByName(String majorName) {
        return majorMapper.findMajorByName(majorName);
    }

    public List<CourseVO> findCoursesByMajor(Major studentMajor) {
        List<Schedule> scheduleList = scheduleMapper.findAll();
        List<CourseVO> courseVOList = new ArrayList<>();
        List<CourseVO> result = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            CourseVO courseVO = classAdapter.fromCourse2CourseVO(courseMapper.findCourseById(schedule.getCourseid()));
            courseVOList.add(courseVO);
        }

        // 去重   // TODO
        LinkedHashSet<CourseVO> hashSet = new LinkedHashSet<>(courseVOList);
        courseVOList = new ArrayList<>(hashSet);

        for (CourseVO courseVO : courseVOList) {
            if (majorQualified(courseVO,studentMajor)) {
                result.add(courseVO);
            }
        }
        return result;
    }

    private boolean majorQualified(CourseVO courseVO, Major studentMajor) {
        return courseAndMajorMapper.findCourseAndMajorByCourseidAndMajorid(courseVO.getCourse().getId(),studentMajor.getId()) != null;
    }

    public Administrator findAdmin(){
        return adminMapper.findAdministratorById(1);
    }


    public Student findStudentByStunum(String stunum) {
        return studentMapper.findStudentByStunum(stunum);
    }

    public List<CourseVO> findCoursesByStudent(Student student) {
        List<StuCourse> stuCourseList = stuCourseMapper.findAllByStudentid(student.getId());
        List<CourseVO> result = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            CourseVO courseVO = classAdapter.fromCourse2CourseVO(courseMapper.findCourseById(stuCourse.getCourseid()));
            result.add(courseVO);
        }

        return result;
    }

    public Course findCourseByCoursenum(String coursenum) {
        return courseMapper.findCourseByCoursenum(coursenum);
    }

    public boolean checkCapacity(Course course,Administrator administrator) {
        //查询当前选课期
        if(administrator.getChoosecourse1().equals("on"))
            return true;
        int capacity = parseInt(course.getCapacity());
        int stuCount = parseInt(course.getCurrentcount());
        return stuCount < capacity;
    }

    public boolean checkSchedule(Student student, Course course) {
        List<StuCourse> stuCourseList = stuCourseMapper.findStuCoursesByStudentid(student.getId());
        List<Schedule> studentScheduleList = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            List<Schedule> schedules = scheduleMapper.findSchedulesByCourseid(stuCourse.getCourseid());
            studentScheduleList.addAll(schedules);
        }

        List<Schedule> courseScheduleList = scheduleMapper.findSchedulesByCourseid(course.getId());
        for (Schedule schedule : courseScheduleList) {
            if (studentScheduleList.contains(schedule)) {   // 挑选的课程的某个上课时间，学生本来就要上课
                return false;
            }
        }

        return true;
    }

    public void choose(Student student, Course course) {
        StuCourse stuCourse = new StuCourse();
        stuCourse.setStudentid(student.getId());
        stuCourse.setCourseid(course.getId());
        stuCourse.setStatus("S");   // 已选S

        course.addStudent();
        courseMapper.save(course);

        stuCourseMapper.save(stuCourse);
    }

    public void delete(Student student, Course course) {
        stuCourseMapper.deleteByStudentidAndCourseid(student.getId(), course.getId());
    }

    public boolean checkStuCourse(Student student, Course course) {
        List<StuCourse> stuCourses = stuCourseMapper.findStuCoursesByStudentidAndCourseid(student.getId(), course.getId());
        return stuCourses.size() > 0;
    }

    public Student findStudentByStudentid(Integer studentid) {
        return studentMapper.findStudentById(studentid);
    }

    public Course findCourseByCourseid(Integer courseid) {
        return courseMapper.findCourseById(courseid);
    }

    public CourseTemplate findCourseTemplateByCourse(Course course) {
        return courseTemplateMapper.findCourseTemplateById(course.getCoursetemplateid());
    }

    public boolean courseTemplateConflict(Student student, Course course) {
        int CourseTemplateId = findCourseTemplateByCourse(course).getId();
        List<CourseVO> list = findCoursesByStudent(student);
        for (CourseVO v : list){
            if(v.getCourseTemplate().getId() == CourseTemplateId)
                return true;
        }
        return false;
    }

    public List<CourseVO> findCoursesByStudentAndStatus(Student student, String s) {
        List<StuCourse> stuCourseList = stuCourseMapper.findAllByStudentidAndStatus(student.getId(),s);
        List<CourseVO> result = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            CourseVO courseVO = classAdapter.fromCourse2CourseVO(courseMapper.findCourseById(stuCourse.getCourseid()));
            result.add(courseVO);
        }
        return result;
    }
}
