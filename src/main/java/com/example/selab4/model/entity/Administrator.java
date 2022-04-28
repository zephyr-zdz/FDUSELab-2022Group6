package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "administrator")
public class Administrator {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "admin_num", nullable = false, length = 45)
    private String adminnum;
    @Column(name = "password", nullable = false, length = 45)
    private String password;
    @Column(name = "select_course_function", nullable = true, length = 5)
    private String selectcoursefunction;    // 暂定on/off
}
