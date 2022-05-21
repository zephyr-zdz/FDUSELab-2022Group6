package com.example.selab4.controller.admin;

import com.example.selab4.model.entity.CourseTemplate;
import com.example.selab4.service.admin.CourseTemplateService;
import com.example.selab4.util.Response;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminCourseTemplateController")
@RequestMapping("/admin/course-template")
public class CourseTemplateController {
    private final CourseTemplateService service;

    @Autowired
    public CourseTemplateController(CourseTemplateService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Response<List<CourseTemplate>> getAll() {
        return service.getAll();
    }

    // 有课程就不能删课程模板
    @DeleteMapping
    public Response<CourseTemplate> delete(@RequestBody CourseTemplate courseTemplate) {
        return service.delete(courseTemplate);
    }

    @PostMapping
    public Response<CourseTemplate> create(@RequestBody CourseTemplate courseTemplate) {
        return service.create(courseTemplate);
    }

    @PutMapping
    public Response<CourseTemplate> update(@RequestBody CourseTemplate courseTemplate) {
        return service.update(courseTemplate);
    }

    @PostMapping("/file")
    public Response<String> createByFile(@RequestParam("file") MultipartFile multipartFile) {
        return service.createByFile(multipartFile);
    }

}
