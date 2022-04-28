package com.example.selab4.controller.student;

import com.example.selab4.model.entity.Student;
import com.example.selab4.service.student.StudentService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentStudentController")
@RequestMapping("/student/student")
public class StudentController {
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Response<Student> get(@RequestParam("stunum") String stunum) {
        return service.get(stunum);
    }

    @PutMapping
    public Response<Student> update(@RequestBody Student student) {
        return service.update(student);
    }

    @PutMapping("/changepassword")
    public Response<String> changePassword(@RequestParam("username") String stunum,
                                           @RequestParam("oldpwd") String oldpwd,
                                           @RequestParam("newpwd") String newpwd) {
        return service.changePassword(stunum, oldpwd, newpwd);
    }
}
