package com.example.selab4.model.vo;

import com.example.selab4.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseApplicationVO {
    private TeacherCourseApplication teacherCourseApplication;
    private Institute institute;
    private Major major;
    private Teacher teacher;
    private Classroom classroom;
}
