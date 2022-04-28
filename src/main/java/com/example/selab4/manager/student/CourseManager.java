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
}
