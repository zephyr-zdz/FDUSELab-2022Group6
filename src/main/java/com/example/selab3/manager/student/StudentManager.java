package com.example.selab3.manager.student;

import com.example.selab3.mapper.StudentMapper;
import com.example.selab3.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("StudentStudentManager")
public class StudentManager {
    private final StudentMapper studentMapper;

    @Autowired
    public StudentManager(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Student getStudentByStunum(String stunum) {
        return studentMapper.findStudentByStunum(stunum);
    }

    public Student getStudentById(Integer id) {
        return studentMapper.findStudentById(id);
    }

    public Student getStudentByStunumAndPassword(String stunum, String password) {
        return studentMapper.findStudentByStunumAndPassword(stunum, password);
    }

    public void update(Student student) {
        studentMapper.save(student);
    }

}
