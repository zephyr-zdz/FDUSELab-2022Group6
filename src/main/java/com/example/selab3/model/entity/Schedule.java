package com.example.selab3.model.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="schedule")
public class Schedule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private Integer id;
    @Column(name = "calendar_id",nullable = false)
    private Integer calendarid;
    @Column(name = "classroom_id",nullable = false)
    private Integer classroomid;
    @Column(name = "teacher_id",nullable = false)
    private Integer teacherid;
    @Column(name = "course_id",nullable = false)
    private Integer courseid;
}
