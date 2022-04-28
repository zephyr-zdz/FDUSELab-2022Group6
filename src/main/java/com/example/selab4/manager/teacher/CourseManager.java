package com.example.selab4.manager.teacher;

import com.example.selab4.mapper.CourseMapper;
import com.example.selab4.mapper.MajorMapper;
import com.example.selab4.mapper.ScheduleMapper;
import com.example.selab4.mapper.TeacherMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Major;
import com.example.selab4.model.entity.Schedule;
import com.example.selab4.model.entity.Teacher;
import com.example.selab4.model.vo.CourseVO;
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
    private final TeacherMapper teacherMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    public CourseManager(CourseMapper courseMapper, MajorMapper majorMapper, ScheduleMapper scheduleMapper, TeacherMapper teacherMapper, ClassAdapter classAdapter) {
        this.courseMapper = courseMapper;
        this.majorMapper = majorMapper;
        this.scheduleMapper = scheduleMapper;
        this.teacherMapper = teacherMapper;
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
}
