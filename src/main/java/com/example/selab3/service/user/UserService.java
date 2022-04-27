package com.example.selab3.service.user;

import com.example.selab3.manager.user.UserManager;
import com.example.selab3.model.entity.Administrator;
import com.example.selab3.model.entity.Student;
import com.example.selab3.model.entity.Teacher;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("UserUserService")
public class UserService {
    UserManager userManager;

    @Autowired
    UserService(UserManager userManager){
        this.userManager = userManager;
    }

    public Response<String> login(String type, String number, String password){
        switch(type){
            case "admin": {
                Administrator administrator = userManager.findAdministratorByAdminnumAndPassword(number, password);
                if (administrator == null) {
                    return new Response<>(Response.FAIL, "管理员身份验证失败", "false");
                }
                return new Response<>(Response.SUCCESS, "管理员登录成功", "true");
            }
            case "student":{
                Student student = userManager.findStudentByStunumAndPassword(number, password);
                if (student == null) {
                    return new Response<>(Response.FAIL, "学生身份验证失败", "false");
                }

                if (student.getStatus().equals("N")) {
                    return new Response<>(Response.FAIL, "学生已毕业，没有登录权限", "false");
                }

                String passwordChanged = student.getInitialized();
                if (Objects.equals(passwordChanged, "N")) {
                    return new Response<>(Response.SUCCESS, "学生登录成功（首次）", "first");
                } else {
                    return new Response<>(Response.SUCCESS, "学生登录成功", "true");
                }
            }
            case "teacher":{
                Teacher teacher = userManager.findTeacherByJobnumAndPassword(number, password);
                if (teacher == null) {
                    return new Response<>(Response.FAIL, "教师身份验证失败", "false");
                }

                if (teacher.getStatus().equals("N")) {
                    return new Response<>(Response.FAIL, "教师已离岗，没有登录权限", "false");
                }

                String passwordChanged = teacher.getInitialized();
                if (Objects.equals(passwordChanged, "N")) {
                    return new Response<>(Response.SUCCESS, "教师登录成功（首次）", "first");
                } else {
                    return new Response<>(Response.SUCCESS, "教师登录成功", "true");
                }
            }
            default:
                return new Response<>(Response.FAIL,"身份类型错误","false");
        }
    }
}
