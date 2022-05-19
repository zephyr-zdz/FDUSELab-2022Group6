package com.example.selab4.manager.student;

import com.example.selab4.mapper.AdminMapper;
import com.example.selab4.mapper.CourseMapper;
import com.example.selab4.mapper.StudentApplicationMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.model.entity.Classroom;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.StudentCourseApplication;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Component("StudentApplicationManager")
public class ApplicationManager {
    private final StudentApplicationMapper studentApplicationMapper;
    private final CourseMapper courseMapper;
    private final ClassAdapter classAdapter;
    private final AdminMapper adminMapper;

    @Autowired
    public ApplicationManager(StudentApplicationMapper studentApplicationMapper, CourseMapper courseMapper, ClassAdapter classAdapter, AdminMapper adminMapper) {
        this.studentApplicationMapper = studentApplicationMapper;
        this.courseMapper = courseMapper;
        this.classAdapter = classAdapter;
        this.adminMapper = adminMapper;
    }


    public void save(StudentCourseApplication studentCourseApplication) {
        studentApplicationMapper.save(studentCourseApplication);
    }

    public List<StudentCourseApplicationVO> getCourseApplicationsByStudentid(Integer studentid) {
        List<StudentCourseApplication> studentCourseApplicationList = studentApplicationMapper.findAllByStudentid(studentid);
        List<StudentCourseApplicationVO> studentCourseApplicationVOList = new ArrayList<>();
        for (StudentCourseApplication studentCourseApplication : studentCourseApplicationList) {
            StudentCourseApplicationVO studentCourseApplicationVO = classAdapter.fromStudentCourseApplication2StudentCourseApplicationVO(studentCourseApplication);
            studentCourseApplicationVOList.add(studentCourseApplicationVO);
        }

        return studentCourseApplicationVOList;
    }
}
