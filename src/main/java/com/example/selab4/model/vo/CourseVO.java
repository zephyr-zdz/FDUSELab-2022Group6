package com.example.selab4.model.vo;

import com.example.selab4.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {
    private Course course;
    private Teacher teacher;
    private List<Schedule> scheduleList;
    private Classroom classroom;
    private Institute institute;
    private Major major;
    private List<Calendar> calendarList;
}
