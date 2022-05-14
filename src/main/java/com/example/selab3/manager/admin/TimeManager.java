package com.example.selab3.manager.admin;

import com.example.selab3.mapper.TimeMapper;
import com.example.selab3.model.entity.TimeTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AdminTimeManager")
public class TimeManager {
    private final TimeMapper timeMapper;

    public TimeManager(TimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    public List<TimeTable> getAll() {
        return timeMapper.findAll();
    }

    public void update(TimeTable timeTable) {
        timeMapper.save(timeTable);
    }

    public TimeTable getTimetableById(Integer id) {
        return timeMapper.findTimetableById(id);
    }
}
