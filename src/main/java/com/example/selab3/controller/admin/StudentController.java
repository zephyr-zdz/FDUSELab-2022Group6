package com.example.selab3.controller.admin;

import com.example.selab3.model.entity.Student;
import com.example.selab3.service.admin.StudentService;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController("AdminStudentController")
@RequestMapping("/admin/student")
public class StudentController {
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Response<List<Student>> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Response<Student> create(@RequestBody Student student) {
        return service.create(student);
    }

    @PutMapping
    public Response<Student> update(@RequestBody Student student) {
        return service.update(student);
    }

    @PostMapping("/file")
    public Response<String> createByFile(@RequestParam("file") MultipartFile multipartFile) {
        return service.createByFile(multipartFile);
    }

    // 如果学生有选课，不允许删除学生
    @DeleteMapping
    public Response<Student> delete(@RequestParam("stunum") String stunum) {
        return service.delete(stunum);
    }

}
