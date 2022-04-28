package com.example.selab4.mapper;
import com.example.selab4.model.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeMapper extends JpaRepository<TimeTable,Integer> {
}
