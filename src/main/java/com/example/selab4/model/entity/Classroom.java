package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classroom")
public class Classroom {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "capacity", nullable = false, length = 45)
    private String capacity;    // 教室的座位数量（老师站讲台上的不算）
    @Column(name = "state",nullable = false)
    private String state;
}
