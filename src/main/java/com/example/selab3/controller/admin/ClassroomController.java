package com.example.selab3.controller.admin;
import com.example.selab3.model.entity.Classroom;
import com.example.selab3.service.admin.ClassroomService;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AdminClassroomController")
@RequestMapping("/admin/Classroom")
public class ClassroomController {
    private final ClassroomService classroomService;

    @Autowired
    ClassroomController(ClassroomService classroomService){
        this.classroomService=classroomService;
    }

    @GetMapping("/getAllClassroom")
    public Response<List<Classroom>> getAllClassrooms(){
        return classroomService.findAllClassrooms();
    }

    @GetMapping("/getOpenClassroom")
    public Response<List<Classroom>> getOpenClassrooms(){
        return classroomService.findOpenClassrooms();
    }


    @GetMapping("/openClassroomState")
    public Response<String> openClassroomState(String Name){
        return classroomService.openClassroomState(Name);
    }

    @GetMapping("/closeClassroomState")
    public Response<String> closeClassroomState(String Name){
        return classroomService.closeClassroomState(Name);
    }
}
