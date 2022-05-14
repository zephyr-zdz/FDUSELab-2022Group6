package com.example.selab3.service.admin;

import com.example.selab3.manager.admin.TimeManager;
import com.example.selab3.model.entity.TimeTable;
import com.example.selab3.util.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminTimeService")
public class TimeService {
    private final TimeManager manager;

    public TimeService(TimeManager manager) {
        this.manager = manager;
    }

    public Response<List<TimeTable>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有时间表内容成功", manager.getAll());
    }

    public Response<TimeTable> update(TimeTable timeTable) {
        manager.update(timeTable);
        return new Response<>(Response.SUCCESS, "修改时间表内容成功", manager.getTimetableById(timeTable.getId()));
    }
}
