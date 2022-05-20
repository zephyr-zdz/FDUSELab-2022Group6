package com.example.selab4.mapper;
import com.example.selab4.model.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorMapper extends JpaRepository<Major, Integer> {
    Major findMajorByName(String name);
    List<Major> findMajorsByInstitute(String institute);
    Major findMajorById(Integer id);
}
