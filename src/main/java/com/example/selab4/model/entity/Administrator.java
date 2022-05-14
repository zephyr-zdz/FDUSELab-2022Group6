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
    @Column(name = "semester", nullable = false, length = 10)
    private String semester;        // 学年学期，例如：2021-2022春

    @Column(name = "choosecourse1", nullable = false, length = 1)
    private String choosecourse1;   // 第一轮选课 开关
    @Column(name = "choosecourse2", nullable = false, length = 1)
    private String choosecourse2;   // 第二轮选课 开关
    @Column(name = "semesterbegin", nullable = false, length = 1)
    private String semesterbegin;   // 学期结束/开始 开关
}
