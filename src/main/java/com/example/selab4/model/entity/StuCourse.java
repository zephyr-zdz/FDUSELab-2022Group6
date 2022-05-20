package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stu_course")
public class StuCourse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "studentid", nullable = false)
    private Integer studentid;
    @Column(name = "courseid", nullable = false)
    private Integer courseid;
    @Column(name = "status", nullable = false, length = 1)
    private String status;  // 已选S；已修F

    public void initialize(Integer courseid, Integer studentid) {
        this.studentid = studentid;
        this.courseid = courseid;
        this.status = "S";
    }
}
