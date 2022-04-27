package com.example.selab3.mapper;
import com.example.selab3.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper extends JpaRepository<Student, Integer> {
    Student findStudentByStunumAndPassword(String stunum, String password);
    Student findStudentByIdentity(String identity);
    Student findStudentByStunum(String stunum);
    Student findStudentById(Integer id);
    List<Student> findStudentsByInstitute(String institute);
    List<Student> findStudentsByMajor(String major);
}
