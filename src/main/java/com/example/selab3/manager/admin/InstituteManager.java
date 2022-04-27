package com.example.selab3.manager.admin;

import com.example.selab3.mapper.InstituteMapper;
import com.example.selab3.mapper.MajorMapper;
import com.example.selab3.mapper.StudentMapper;
import com.example.selab3.mapper.TeacherMapper;
import com.example.selab3.model.entity.Institute;
import com.example.selab3.model.entity.Major;
import com.example.selab3.model.entity.Student;
import com.example.selab3.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AdminInstituteManager")
public class InstituteManager {
    private final InstituteMapper instituteMapper;
    private final MajorMapper majorMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    @Autowired
    public InstituteManager(InstituteMapper instituteMapper, MajorMapper majorMapper,
                            StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.instituteMapper = instituteMapper;
        this.majorMapper = majorMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public Institute getInstituteByName(String name) {
        return instituteMapper.findInstituteByName(name);
    }

    public Institute getInstituteById(Integer id) {
        return instituteMapper.findInstituteById(id);
    }

    public List<Institute> getAllInstitutes() {
        return instituteMapper.findAll();
    }

    public List<Major> getMajors(Institute institute) {
        return majorMapper.findMajorsByInstitute(institute.getName());
    }

    public void createInstitute(Institute institute) {
        instituteMapper.save(institute);
    }

    public void updateInstitute(Institute institute) {
        instituteMapper.save(institute);
    }

    public void updateMajorsByInstitute(Institute oldInstitute, Institute institute) {
        List<Major> majors = majorMapper.findMajorsByInstitute(oldInstitute.getName());
        for (Major major : majors) {
            major.setInstitute(institute.getName());
            majorMapper.save(major);
        }
    }

    public void updateStudentsByInstitute(Institute oldInstitute, Institute institute) {
        List<Student> students = studentMapper.findStudentsByInstitute(oldInstitute.getName());
        for (Student student : students) {
            student.setInstitute(institute.getName());
            studentMapper.save(student);
        }
    }

    public void updateTeachersByInstitute(Institute oldInstitute, Institute institute) {
        List<Teacher> teachers = teacherMapper.findTeachersByInstitute(oldInstitute.getName());
        for (Teacher teacher : teachers) {
            teacher.setInstitute(institute.getName());
            teacherMapper.save(teacher);
        }
    }

    public void deleteInstitute(Institute institute) {
        instituteMapper.delete(institute);
    }

}
