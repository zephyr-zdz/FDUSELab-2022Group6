package com.example.selab4.controller.admin;
import com.example.selab4.model.entity.Classroom;
import com.example.selab4.service.admin.ClassroomService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminClassroomController")
@RequestMapping("/admin/classroom")
public class ClassroomController {
    private final ClassroomService classroomService;

    @Autowired
    ClassroomController(ClassroomService classroomService){
        this.classroomService=classroomService;
    }

    @GetMapping("/all")
    public Response<List<Classroom>> getAllClassrooms(){
        return classroomService.findAllClassrooms();
    }

    @GetMapping("/open")
    public Response<List<Classroom>> getOpenClassrooms(){
        return classroomService.findOpenClassrooms();
    }


    @PutMapping("/open")
    public Response<String> openClassroomState(@RequestParam("classroom-name") String Name){
        return classroomService.openClassroomState(Name);
    }

    // 这些教室必须没有课上，才能修改
    @PutMapping("/close")
    public Response<String> closeClassroomState(@RequestParam("classroom-name") String Name){
        return classroomService.closeClassroomState(Name);
    }
}
