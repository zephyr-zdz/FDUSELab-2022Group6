package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_course_application")
public class StudentCourseApplication {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "studentid", nullable = false)
    private Integer studentid;
    @Column(name = "courseid", nullable = false)
    private Integer courseid;
    @Column(name = "result", nullable = false, length = 10)
    private String result;  // pending/reject/approve
    @Lob
    @Column(name = "explanation", nullable = false, length = 65535)
    private String explanation;
}
