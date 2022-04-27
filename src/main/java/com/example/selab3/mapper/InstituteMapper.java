package com.example.selab3.mapper;

import com.example.selab3.model.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteMapper extends JpaRepository<Institute, Integer> {
    Institute findInstituteByName(String name);
    Institute findInstituteById(Integer id);
}
