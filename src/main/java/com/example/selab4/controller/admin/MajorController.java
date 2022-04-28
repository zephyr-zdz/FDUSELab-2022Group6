package com.example.selab4.controller.admin;

import com.example.selab4.model.entity.Major;
import com.example.selab4.service.admin.MajorService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminMajorController")
@RequestMapping("/admin/major")
public class MajorController {
    private final MajorService service;

    @Autowired
    public MajorController(MajorService service) {
        this.service = service;
    }

    @GetMapping
    public Response<Major> get(@RequestParam("major") String majorName) {
        return service.get(majorName);
    }

    @GetMapping("/all")
    public Response<List<Major>> getAll() {
        return service.getAll();
    }

    @GetMapping("/allbyinstitute")
    public Response<List<Major>> getAllByInstitute(@RequestParam("institute") String instituteName) {
        return service.getAllByInstitute(instituteName);
    }

    @PostMapping
    public Response<Major> create(@RequestBody Major major) {
        return service.create(major);
    }

    // 如果专业还有学生/教师，不允许删除专业(此处可以不考虑课程申请)
    @DeleteMapping
    public Response<Major> delete(@RequestParam("major") String majorName) {
        return service.delete(majorName);
    }

    // 修改专业会造成级联的影响(学生/教师)
    @PutMapping
    public Response<Major> update(@RequestBody Major major) {
        return service.update(major);
    }

}
