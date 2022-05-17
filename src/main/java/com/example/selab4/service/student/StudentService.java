package com.example.selab4.service.student;

import com.example.selab4.manager.student.StudentManager;
import com.example.selab4.model.entity.Student;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StudentStudentService")
public class StudentService {
    private final StudentManager manager;

    @Autowired
    public StudentService(StudentManager manager) {
        this.manager = manager;
    }

    public Response<Student> get(String stunum) {
        Student student = manager.getStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找学生成功", student);
    }

    public Response<Student> update(Student student) {
        Student oldStudent = manager.getStudentById(student.getId());
        if (oldStudent == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在" ,null);
        }

        if (!student.getName().equals(oldStudent.getName())) {
            return new Response<>(Response.FAIL, "姓名不应被修改", null);
        }

        if (!student.getIdentity().equals(oldStudent.getIdentity())) {
            return new Response<>(Response.FAIL, "身份证号不应修改", null);
        }

        if (!student.getStunum().equals(oldStudent.getStunum())) {
            return new Response<>(Response.FAIL, "学号不应被修改", null);
        }

        if (!student.getInitialized().equals(oldStudent.getInitialized())) {
            return new Response<>(Response.FAIL, "密码是否初始化的状态不应被修改", null);
        }

        if (!student.getMajor().equals(oldStudent.getMajor())) {
            return new Response<>(Response.FAIL, "专业不应被修改", null);
        }

        if (!student.getInstitute().equals(oldStudent.getInstitute())) {
            return new Response<>(Response.FAIL, "学院不应被修改", null);
        }

        if (!student.getStatus().equals(oldStudent.getStatus())) {
            return new Response<>(Response.FAIL, "学生是否毕业的状态不应被修改", null);
        }

        if (student.getTel().length() == 0) {
            student.setTel("NA");
        }

        if (student.getEmail().length() == 0) {
            student.setEmail("NA");
        }

        oldStudent.update(student);
        manager.update(oldStudent);
        return new Response<>(Response.SUCCESS, "修改学生信息成功", oldStudent);
    }

    public Response<String> changePassword(String stunum, String oldpwd, String newpwd) {
        Student student = manager.getStudentByStunumAndPassword(stunum, oldpwd);
        if (student == null) {
            return new Response<>(Response.FAIL, "学生身份验证失败", "false");
        }

        student.setPassword(newpwd);
        student.setInitialized("Y");
        manager.update(student);
        return new Response<>(Response.SUCCESS, "学生密码修改成功", "true");
    }
}
