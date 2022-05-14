package com.example.selab3.manager.student;

import com.example.selab3.mapper.*;
import com.example.selab3.util.ClassAdapter;
import com.example.selab3.model.entity.*;
import com.example.selab3.model.vo.CourseVO;
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

    @Autowired
    public CourseManager(MajorMapper majorMapper, ScheduleMapper scheduleMapper, CourseMapper courseMapper, AdminMapper adminMapper, StudentMapper studentMapper, StuCourseMapper stuCourseMapper, ClassAdapter classAdapter) {
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.courseMapper = courseMapper;
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.classAdapter = classAdapter;
    }

    public Major findMajorByName(String majorName) {
        return majorMapper.findMajorByName(majorName);
    }

    public List<CourseVO> findCoursesByMajor(Major major) {
        List<Schedule> scheduleList = scheduleMapper.findAll();
        List<CourseVO> courseVOList = new ArrayList<>();
        List<CourseVO> result = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            CourseVO courseVO = classAdapter.fromCourse2CourseVO(courseMapper.findCourseById(schedule.getCourseid()));
            courseVOList.add(courseVO);
        }

        LinkedHashSet<CourseVO> hashSet = new LinkedHashSet<>(courseVOList);
        courseVOList = new ArrayList<>(hashSet);

        for (CourseVO courseVO : courseVOList) {
            if (courseVO.getMajor().equals(major)) {
                result.add(courseVO);
            }
        }

        return result;
    }

    public Administrator findAdminById(Integer id){
        return adminMapper.findAdministratorById(id);
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

    public boolean checkCapacity(Course course) {
        Integer capacity = parseInt(course.getCapacity());
        Integer stuCount = stuCourseMapper.countByCourseid(course.getId());
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
        stuCourseMapper.save(stuCourse);
    }

    public void delete(Student student, Course course) {
        stuCourseMapper.deleteByStudentidAndCourseid(student.getId(), course.getId());
    }

    public boolean checkStuCourse(Student student, Course course) {
        List<StuCourse> stuCourses = stuCourseMapper.findStuCoursesByStudentidAndCourseid(student.getId(), course.getId());
        return stuCourses.size() > 0;
    }
}
