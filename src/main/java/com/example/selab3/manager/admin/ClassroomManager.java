package com.example.selab3.manager.admin;

import com.example.selab3.mapper.ClassroomMapper;
import com.example.selab3.model.entity.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AdminClassroomManager")
public class ClassroomManager {
    private final ClassroomMapper classroomMapper;

    @Autowired
    ClassroomManager(ClassroomMapper classroomMapper){
        this.classroomMapper=classroomMapper;
    }

    public Classroom findClassroomByName(String name){
        return classroomMapper.findClassroomByName(name);
    }

    public List<Classroom> findAllClassroom(){
        return classroomMapper.findAll();
    }

    public List<Classroom> findOpenClassroom(){
        return classroomMapper.findClassroomByState("on");
    }

    public void save(Classroom classroom) {
        classroomMapper.save(classroom);
    }
}
