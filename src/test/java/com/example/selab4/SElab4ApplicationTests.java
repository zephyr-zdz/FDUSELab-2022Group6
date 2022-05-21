package com.example.selab4;

import com.example.selab4.mapper.CourseTemplateMapper;
import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.model.entity.CourseTemplate;
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
    CourseTemplateMapper courseTemplateMapper;

    @Autowired
    SElab4ApplicationTests(StudentMapper studentMapper,CourseTemplateMapper courseTemplateMapper){
        this.studentMapper=studentMapper;
        this.courseTemplateMapper=courseTemplateMapper;
    }

    @Test
    void testInsertStudent() {
        Student student = new Student();
        // 如果student的id是已经存在的，save就是修改
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

        // 观察数据表，此时id为1的那条数据，信息被修改了，但id仍为1
        studentMapper.save(student);

        // 如果student的id是不存在的，save就是新增
        student.setId(10000);

        // 观察数据表，此时数据表新增了一条数据，但它的id不是10000，而是数据库自动生成的一个自增id
        studentMapper.save(student);
    }

    @Test
    void testUpdateCourseTemplate(){
        CourseTemplate courseTemplate = new CourseTemplate();
        courseTemplate.setCoursenum("000011");
        courseTemplate.setName("离散数学");
        courseTemplateMapper.save(courseTemplate);
    }
}
