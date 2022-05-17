package com.example.selab4.model.checker;

import com.example.selab4.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleChecker {
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleChecker(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }


}
