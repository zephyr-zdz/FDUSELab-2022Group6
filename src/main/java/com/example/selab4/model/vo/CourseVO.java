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
    private CourseTemplate courseTemplate;
    private Teacher teacher;
    private List<Schedule> scheduleList;
    private Classroom classroom;
    private Institute instituteOfTeacher;
    private Major majorOfTeacher;
    private List<Calendar> calendarList;
    private List<Major> majorListOfCourse;
 }

