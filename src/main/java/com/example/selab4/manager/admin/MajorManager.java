package com.example.selab4.manager.admin;

import com.example.selab4.mapper.InstituteMapper;
import com.example.selab4.mapper.MajorMapper;
import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.mapper.TeacherMapper;
import com.example.selab4.model.entity.Institute;
import com.example.selab4.model.entity.Major;
import com.example.selab4.model.entity.Student;
import com.example.selab4.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("AdminMajorManager")
public class MajorManager {
    private final MajorMapper majorMapper;
    private final InstituteMapper instituteMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    @Autowired
    public MajorManager(MajorMapper majorMapper, InstituteMapper instituteMapper,
                        StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.majorMapper = majorMapper;
        this.instituteMapper = instituteMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public Major getMajorByName(String name) {
        return majorMapper.findMajorByName(name);
    }

    public Major getMajorById(Integer id) { return majorMapper.findMajorById(id); }

    public List<Major> getAllMajors() {
        return majorMapper.findAll();
    }

    public List<Major> getAllMajorsByInstitute(String institute) {
        return majorMapper.findMajorsByInstitute(institute);
    }

    public List<Student> getStudentsByMajor(Major major) {
        return studentMapper.findStudentsByMajor(major.getName());
    }

    public List<Teacher> getTeachersByMajor(Major major) {
        return teacherMapper.findTeachersByMajor(major.getName());
    }

    public void createMajor(Major major) {
        majorMapper.save(major);
    }

    public void deleteMajor(Major major) {
        majorMapper.deleteById(major.getId());
    }

    public Institute getInstituteByName(String instituteName) {
        return instituteMapper.findInstituteByName(instituteName);
    }

    public void updateMajor(Major major) {
        majorMapper.save(major);
    }

    public void updateStudentsByMajor(Major oldMajor, Major major) {
        List<Student> students = studentMapper.findStudentsByMajor(oldMajor.getName());
        for (Student student : students) {
            student.setMajor(major.getName());
            studentMapper.save(student);
        }
    }

    public void updateTeachersByMajor(Major oldMajor, Major major) {
        List<Teacher> teachers = teacherMapper.findTeachersByMajor(oldMajor.getName());
        for (Teacher teacher : teachers) {
            teacher.setMajor(major.getName());
            teacherMapper.save(teacher);
        }
    }
}
