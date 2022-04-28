package com.example.selab4.mapper;
import com.example.selab4.model.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomMapper extends JpaRepository<Classroom,Integer> {
    Classroom findClassroomById(Integer id);

    Classroom findClassroomByName(String name);

    List<Classroom> findClassroomByState(String state);
}
