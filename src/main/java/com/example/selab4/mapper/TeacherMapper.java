package com.example.selab4.mapper;
import com.example.selab4.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper extends JpaRepository<Teacher, Integer> {
    Teacher findTeacherByJobnumAndPassword(String jobnum, String password);
    Teacher findTeacherByIdentity(String identity);
    Teacher findTeacherByJobnum(String jobnum);
    Teacher findTeacherById(Integer id);
    List<Teacher> findTeachersByInstitute(String institute);
    List<Teacher> findTeachersByMajor(String major);
}
