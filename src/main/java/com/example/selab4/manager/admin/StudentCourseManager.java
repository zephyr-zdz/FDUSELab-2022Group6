package com.example.selab4.manager.admin;

import com.example.selab4.mapper.StuCourseMapper;
import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.model.ClassAdapter;
import com.example.selab4.model.entity.StuCourse;
import com.example.selab4.model.vo.StudentCourseApplicationVO;
import com.example.selab4.model.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AdminStudentCourseManager")
public class StudentCourseManager {
    private final StuCourseMapper stuCourseMapper;
    private final StudentMapper studentMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    public StudentCourseManager(StuCourseMapper stuCourseMapper, StudentMapper studentMapper, ClassAdapter classAdapter) {
        this.stuCourseMapper = stuCourseMapper;
        this.studentMapper = studentMapper;
        this.classAdapter = classAdapter;
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

    public List<StudentCourseApplicationVO> getStudentCourseApplicationVOList() {
        return null;
    }
}
