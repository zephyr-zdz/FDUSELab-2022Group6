package com.example.selab4;

import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.model.entity.Student;
import com.example.selab4.service.admin.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest(classes = SElab4Application.class)
@RunWith(SpringRunner.class)
class SElab4ApplicationTests {

    StudentMapper studentMapper;

    @Autowired
    SElab4ApplicationTests(StudentMapper studentMapper){
        this.studentMapper=studentMapper;
    }

    @Test
    void testInsertStudent() {
        Student student = new Student();
        student.setId(1);
        student.setEmail("测试邮箱");
        student.setIdentity("测试身份证");
        student.setInitialized("N");
        student.setInstitute("测试学院");
        student.setMajor("测试专业");
        student.setName("测试名字");
        student.setPassword("测试密码");
        student.setStunum("测试学号");
        student.setTel("测试电话");
        student.setStatus("N");

        studentMapper.save(student);
    }
}
