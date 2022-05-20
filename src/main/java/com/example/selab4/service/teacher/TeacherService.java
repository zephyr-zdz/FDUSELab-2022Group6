package com.example.selab4.service.teacher;

import com.example.selab4.manager.teacher.TeacherManager;
import com.example.selab4.model.entity.Teacher;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TeacherTeacherService")
public class TeacherService {
    private final TeacherManager manager;

    @Autowired
    public TeacherService(TeacherManager manager) {
        this.manager = manager;
    }

    public Response<Teacher> get(String jobnum) {
        Teacher teacher = manager.getTeacherByJobnum(jobnum);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "工号: " + jobnum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找教师成功", teacher);
    }

    public Response<Teacher> update(Teacher teacher) {
        Teacher oldTeacher = manager.getTeacherById(teacher.getId());
        if (oldTeacher == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在", null);
        }

        if (!teacher.getName().equals(oldTeacher.getName())) {
            return new Response<>(Response.FAIL, "姓名不应被修改", null);
        }

        if (!teacher.getIdentity().equals(oldTeacher.getIdentity())) {
            return new Response<>(Response.FAIL, "身份证号不应修改", null);
        }

        if (!teacher.getJobnum().equals(oldTeacher.getJobnum())) {
            return new Response<>(Response.FAIL, "工号不应被修改", null);
        }

        if (!teacher.getInitialized().equals(oldTeacher.getInitialized())) {
            return new Response<>(Response.FAIL, "密码是否初始化的状态不应被修改", null);
        }

        if (!teacher.getMajor().equals(oldTeacher.getMajor())) {
            return new Response<>(Response.FAIL, "专业不应被修改", null);
        }

        if (!teacher.getInstitute().equals(oldTeacher.getInstitute())) {
            return new Response<>(Response.FAIL, "学院不应被修改", null);
        }

        if (!teacher.getStatus().equals(oldTeacher.getStatus())) {
            return new Response<>(Response.FAIL, "学生是否毕业的状态不应被修改", null);
        }

        if (teacher.getTel().length() == 0) {
            teacher.setTel("NA");
        }

        if (teacher.getEmail().length() == 0) {
            teacher.setEmail("NA");
        }

        oldTeacher.update(teacher);
        manager.update(oldTeacher);
        return new Response<>(Response.SUCCESS, "修改教师信息成功", oldTeacher);
    }

    public Response<String> changePassword(String jobnum, String oldpwd, String newpwd) {
        Teacher teacher = manager.getTeacherByJobnumAndPassword(jobnum, oldpwd);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "教师身份验证失败", "false");
        }

        teacher.setPassword(newpwd);
        teacher.setInitialized("Y");
        manager.update(teacher);
        return new Response<>(Response.SUCCESS, "教师修改密码成功", "true");
    }
}
