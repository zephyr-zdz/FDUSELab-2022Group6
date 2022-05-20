package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_and_major")
public class CourseAndMajor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "courseid", nullable = false)
    private Integer courseid;
    @Column(name = "majorid", nullable = false)
    private Integer majorid;


}
