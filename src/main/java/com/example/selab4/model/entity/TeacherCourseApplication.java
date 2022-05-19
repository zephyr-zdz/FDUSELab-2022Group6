package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher_course_application")
public class TeacherCourseApplication {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "coursetemplateid", nullable = false, length = 10)
    private Integer coursetemplateid;
    @Column(name = "ispublic", nullable = false, length = 1)
    private String ispublic;    // Y/N
    @Column(name = "coursehour", nullable = false, length = 45)
    private String coursehour;  // 学时
    @Column(name = "credit", nullable = false, length = 45)
    private String credit;  // 学分
    @Column(name = "teacherid", nullable = false, length = 45)
    private Integer teacherid;   // 教师的id（不是教师的工号）
    @Lob
    @Column(name = "intro", nullable = true, length = 65535)
    private String intro;
    @Column(name = "schedule", nullable = false, length = 45)
    private String schedule; // 规范：1,13 2,5
    @Column(name = "classroomid", nullable = false, length = 45)
    private Integer classroomid;
    @Column(name = "capacity", nullable = false, length = 45)
    private String capacity;
    @Column(name = "applytype", nullable = false, length = 45)
    private String applytype; // 申请的类型：对课程进行增/删/改
    @Column(name = "result", nullable = false, length = 45)
    private String result;  // 申请的结果：pending/approve/reject
    @Column(name = "applytime", nullable = false, length = 45)
    private String applytime;   // 申请时间（以此作为列表的排序依据// ）
    @Column(name = "precourseid",nullable = false)
    private Integer precourseid;
    @Column(name = "semester", nullable = false, length = 10)
    private String semester;
    @Column(name = "majoridlist", nullable = false, length = 45)
    private String majoridlist; // 规范：1,2,3
}

