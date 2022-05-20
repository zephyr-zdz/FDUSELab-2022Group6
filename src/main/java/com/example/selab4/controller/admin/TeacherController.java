package com.example.selab4.controller.admin;

import com.example.selab4.model.entity.Teacher;
import com.example.selab4.service.admin.TeacherService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminTeacherController")
@RequestMapping("/admin/teacher")
public class TeacherController {
    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Response<List<Teacher>> getAll() {
        return service.getAll();
    }

    @GetMapping("/id")
    public Response<Teacher> getById(@RequestParam("teacherId") Integer teacherId) {
        return service.getById(teacherId);
    }

    @GetMapping("/jobnum")
    public Response<Teacher> getByJobnum(@RequestParam("jobnum") String jobnum) {
        return service.getByJobnum(jobnum);
    }

    @PostMapping
    public Response<Teacher> create(@RequestBody Teacher teacher) {
        return service.create(teacher);
    }

    @PutMapping
    public Response<Teacher> update(@RequestBody Teacher teacher) {
        return service.update(teacher);
    }

    @PostMapping("/file")
    public Response<String> createByFile(@RequestParam("file") MultipartFile multipartFile) {
        return service.createByFile(multipartFile);
    }

    // 如果教师有上课，或者有发出的课程申请，不允许删除教师
    @DeleteMapping
    public Response<Teacher> delete(@RequestParam("teacherid") Integer teacherId) {
        return service.delete(teacherId);
    }
}
