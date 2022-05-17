package com.example.selab4.controller.admin;

import com.example.selab4.model.entity.Institute;
import com.example.selab4.service.admin.InstituteService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminInstituteController")
@RequestMapping("/admin/institute")
public class InstituteController {
    private final InstituteService service;

    @Autowired
    public InstituteController(InstituteService service) {
        this.service = service;
    }

    @GetMapping("/name")
    public Response<Institute> getByName(@RequestParam("instituteName") String instituteName) {
        return service.getByName(instituteName);
    }

    @GetMapping("/id")
    public Response<Institute> getById(@RequestParam("instituteId") Integer instituteId) {
        return service.getById(instituteId);
    }

    @GetMapping("/all")
    public Response<List<Institute>> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Response<Institute> create(@RequestBody Institute institute) {
        return service.create(institute);
    }

    // 如果学院还有专业，不允许删除学院(此处可以不考虑学生/教师)
    @DeleteMapping
    public Response<Institute> delete(@RequestParam("instituteid") Integer instituteId) {
        return service.delete(instituteId);
    }

    // 修改学院会造成级联的修改(专业、学生/教师)
    @PutMapping()
    public Response<Institute> update(@RequestBody Institute institute) {
        return service.update(institute);
    }

    @GetMapping("/getNameById")
    public Response<String> getNameById(Integer id){
        return new Response<>(0,"success", service.getNameById(id));
    }

    @PostMapping("/file")
    public Response<String> createByFile(@RequestParam("file") MultipartFile multipartFile) {
        return service.createByFile(multipartFile);
    }
}
