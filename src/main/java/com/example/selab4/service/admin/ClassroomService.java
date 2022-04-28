package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.ClassroomManager;
import com.example.selab4.model.entity.Classroom;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminClassroomService")
public class ClassroomService {
    private final ClassroomManager classroomManager;

    @Autowired
    ClassroomService(ClassroomManager classroomManager){
        this.classroomManager=classroomManager;
    }

    public Response<List<Classroom>> findAllClassrooms(){
        return new Response<>(Response.SUCCESS,"success",this.classroomManager.findAllClassroom());
    }

    public Response<List<Classroom>> findOpenClassrooms(){
        return new Response<>(Response.SUCCESS,"success",this.classroomManager.findOpenClassroom());
    }

    public Response<String> openClassroomState(String Name){
        Classroom classroom=classroomManager.findClassroomByName(Name);
        classroom.setState("on");
        classroomManager.save(classroom);
        return new Response<>(Response.SUCCESS,"success","on");
    }

    public Response<String> closeClassroomState(String Name){
        Classroom classroom=classroomManager.findClassroomByName(Name);
        classroom.setState("off");
        classroomManager.save(classroom);
        return new Response<>(Response.SUCCESS,"success","off");
    }
}
