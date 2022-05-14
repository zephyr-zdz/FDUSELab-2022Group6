package com.example.selab4;

import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.model.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
class SElab4ApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void makeFile(String filename, String content) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(content);
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("helloworld222");

        makeFile( "测试输出.txt", stringBuilder.toString());
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
