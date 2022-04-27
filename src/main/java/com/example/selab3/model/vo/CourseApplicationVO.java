package com.example.selab3.model.vo;

import com.example.selab3.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseApplicationVO {
    private CourseApplication courseApplication;
    private Institute institute;
    private Major major;
    private Teacher teacher;
    private Classroom classroom;
}
