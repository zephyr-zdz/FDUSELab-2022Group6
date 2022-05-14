package com.example.selab3.controller.admin;

import com.example.selab3.model.entity.TimeTable;
import com.example.selab3.service.admin.TimeService;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/time")
public class TimeController {
    private final TimeService service;

    @Autowired
    public TimeController(TimeService service) {
        this.service = service;
    }

    @GetMapping
    public Response<List<TimeTable>> getAll() {
        return service.getAll();
    }

    @PutMapping
    public Response<TimeTable> update(@RequestBody TimeTable timeTable) {
        return service.update(timeTable);
    }
}
