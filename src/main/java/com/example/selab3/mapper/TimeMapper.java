package com.example.selab3.mapper;
import com.example.selab3.model.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeMapper extends JpaRepository<TimeTable,Integer> {
    TimeTable findTimetableById(Integer id);
}
