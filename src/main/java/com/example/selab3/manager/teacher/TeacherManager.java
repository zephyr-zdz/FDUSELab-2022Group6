package com.example.selab3.manager.teacher;

import com.example.selab3.mapper.TeacherMapper;
import com.example.selab3.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TeacherTeacherManager")
public class TeacherManager {
    private final TeacherMapper teacherMapper;


    @Autowired
    public TeacherManager(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public Teacher getTeacherByJobnum(String jobnum) {
        return teacherMapper.findTeacherByJobnum(jobnum);
    }

    public Teacher getTeacherById(Integer id) {
        return teacherMapper.findTeacherById(id);
    }

    public Teacher getTeacherByJobnumAndPassword(String jobnum, String password) {
        return teacherMapper.findTeacherByJobnumAndPassword(jobnum, password);
    }

    public void update(Teacher teacher) {
        teacherMapper.save(teacher);
    }
}
