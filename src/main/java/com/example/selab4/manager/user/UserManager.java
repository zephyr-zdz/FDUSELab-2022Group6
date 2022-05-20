package com.example.selab4.manager.user;
import com.example.selab4.mapper.AdminMapper;
import com.example.selab4.mapper.StudentMapper;
import com.example.selab4.mapper.TeacherMapper;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.model.entity.Student;
import com.example.selab4.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserUserManager")
public class UserManager {
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final AdminMapper adminMapper;

    @Autowired
    UserManager(StudentMapper studentMapper, TeacherMapper teacherMapper, AdminMapper adminMapper){
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.adminMapper = adminMapper;
    }

    public Administrator findAdministratorByAdminnumAndPassword(String number, String password) {
        return adminMapper.findAdministratorByAdminnumAndPassword(number, password);
    }

    public Student findStudentByStunumAndPassword(String number, String password) {
        return studentMapper.findStudentByStunumAndPassword(number, password);
    }

    public Teacher findTeacherByJobnumAndPassword(String number, String password) {
        return teacherMapper.findTeacherByJobnumAndPassword(number, password);
    }
}
