package com.example.selab4.manager.admin;

import com.example.selab4.mapper.*;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.checker.ScheduleChecker;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.model.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Component("AdminStudentCourseManager")
public class StudentCourseManager {
    private final StuCourseMapper stuCourseMapper;
    private final StudentMapper studentMapper;
    private final StudentApplicationMapper studentApplicationMapper;
    private final ClassAdapter classAdapter;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentCourseManager(StuCourseMapper stuCourseMapper, StudentMapper studentMapper, StudentApplicationMapper studentApplicationMapper, ClassAdapter classAdapter, CourseMapper courseMapper, AdminMapper adminMapper, ScheduleChecker scheduleChecker) {
        this.stuCourseMapper = stuCourseMapper;
        this.studentMapper = studentMapper;
        this.studentApplicationMapper = studentApplicationMapper;

        this.classAdapter = classAdapter;
        this.courseMapper = courseMapper;
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

    public Course getCourseByCourseid(Integer courseid) {
        return courseMapper.findCourseById(courseid);
    }
    public List<StudentCourseApplicationVO> getStudentCourseApplicationVOList() {
        List<StudentCourseApplication> studentCourseApplicationList = studentApplicationMapper.findAll();
        List<StudentCourseApplicationVO> studentCourseApplicationVOList = new ArrayList<>();
        for (StudentCourseApplication studentCourseApplication : studentCourseApplicationList) {
            StudentCourseApplicationVO studentCourseApplicationVO = classAdapter.fromStudentCourseApplication2StudentCourseApplicationVO(studentCourseApplication);
            studentCourseApplicationVOList.add(studentCourseApplicationVO);
        }

        return studentCourseApplicationVOList;
    }


    public void save(StudentCourseApplication studentCourseApplication) {
        studentApplicationMapper.save(studentCourseApplication);
    }

    public void save(StuCourse stuCourse) {
        stuCourseMapper.save(stuCourse);
    }

    public void save(Course course) {
        courseMapper.save(course);
    }


    public List<StudentCourseApplicationVO> getStudentPendingCourseApplicationVOList() {
        List<StudentCourseApplication> studentPendingCourseApplicationList = studentApplicationMapper.findAllByResult("pending");
        List<StudentCourseApplicationVO> studentPendingCourseApplicationVOList = new ArrayList<>();
        for (StudentCourseApplication studentCourseApplication : studentPendingCourseApplicationList) {
            StudentCourseApplicationVO studentCourseApplicationVO = classAdapter.fromStudentCourseApplication2StudentCourseApplicationVO(studentCourseApplication);
            studentPendingCourseApplicationVOList.add(studentCourseApplicationVO);
        }
        return studentPendingCourseApplicationVOList;
    }
}
