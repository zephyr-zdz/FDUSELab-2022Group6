package com.example.selab4.model.vo;

import com.example.selab4.model.entity.Institute;
import com.example.selab4.model.entity.Major;
import com.example.selab4.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private Student student;
    private Major major;
    private Institute institute;
}
