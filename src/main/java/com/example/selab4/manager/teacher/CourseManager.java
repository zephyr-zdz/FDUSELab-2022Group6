package com.example.selab4.manager.teacher;

import com.example.selab4.mapper.*;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Major;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.model.entity.StuCourse;
import com.example.selab4.model.entity.Teacher;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.model.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Component("TeacherCourseManager")
public class CourseManager {
    private final CourseMapper courseMapper;
    private final MajorMapper majorMapper;
    private final ScheduleMapper scheduleMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final StuCourseMapper stuCourseMapper;
    private final ClassAdapter classAdapter;



    @Autowired
    public CourseManager(CourseMapper courseMapper, MajorMapper majorMapper, ScheduleMapper scheduleMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, StuCourseMapper stuCourseMapper, ClassAdapter classAdapter) {
        this.courseMapper = courseMapper;
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
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
            if (courseVO.getMajorOfTeacher().equals(major)) {
                result.add(courseVO);
            }
        }

        return result;
    }

    public Teacher findTeacherByJobnum(String jobnum) {
        return teacherMapper.findTeacherByJobnum(jobnum);
    }

    public List<CourseVO> findCoursesByTeacher(Teacher teacher) {
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
            if (courseVO.getTeacher().equals(teacher)) {
                result.add(courseVO);
            }
        }

        return result;
    }

    public List<StudentVO> getStudentListByCourseid(Integer courseid) {
        List<StuCourse> stuCourseList = stuCourseMapper.findStuCoursesByCourseidAndStatus(courseid, "S");
        List<StudentVO> studentVOList = new ArrayList<>();
        for (StuCourse stuCourse : stuCourseList) {
            StudentVO studentVO = classAdapter.fromStudent2StudentVO(studentMapper.findStudentById(stuCourse.getStudentid()));
            studentVOList.add(studentVO);
        }

        return studentVOList;
    }
}
