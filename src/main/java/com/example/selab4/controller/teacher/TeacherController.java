package com.example.selab4.controller.teacher;

import com.example.selab4.model.entity.Teacher;
import com.example.selab4.service.teacher.TeacherService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("TeacherTeacherController")
@RequestMapping("/teacher/teacher")
public class TeacherController {
    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public Response<Teacher> get(@RequestParam("jobnum") String jobnum) {
        return service.get(jobnum);
    }

    @PutMapping
    public Response<Teacher> update(@RequestBody Teacher teacher) {
        return service.update(teacher);
    }

    @PutMapping("/changepassword")
    public Response<String> changePassword(@RequestParam("username") String jobnum,
                                           @RequestParam("oldpwd") String oldpwd,
                                           @RequestParam("newpwd") String newpwd) {
        return service.changePassword(jobnum, oldpwd, newpwd);
    }

}
