package com.example.selab4.model.vo;

import com.example.selab4.model.entity.Student;
import com.example.selab4.model.entity.StudentCourseApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseApplicationVO {
    private StudentCourseApplication studentCourseApplication;
    private Student student;
    private CourseVO courseVO;
}
