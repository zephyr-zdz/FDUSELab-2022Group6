package com.example.selab3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student", indexes = {
        @Index(name = "Identity_UNIQUE", columnList = "identity", unique = true),
        @Index(name = "Stunum_UNIQUE", columnList = "stunum", unique = true)
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 15)
    private String name;
    @Column(name = "identity", nullable = false, length = 18)
    private String identity;
    @Column(name = "stunum", nullable = false, length = 6)
    private String stunum;
    @Column(name = "password", length = 30, nullable = true)
    private String password;
    @Column(name = "initialized", length = 1, nullable = true)
    private String initialized; // Y/N
    @Column(name = "email", length = 30, nullable = true)
    private String email;
    @Column(name = "tel", length = 15, nullable = true)
    private String tel;
    @Column(name = "major", length = 45, nullable = true)
    private String major;
    @Column(name = "institute", length = 45, nullable = false)
    private String institute;
    @Column(name = "status", nullable = true, length = 1)
    private String status;  // Y/N

    public void update(Student newStudent) {
        setName(newStudent.getName());
        setIdentity(newStudent.getIdentity());
        setStunum(newStudent.getStunum());
        setPassword(newStudent.getPassword());
        setInitialized(newStudent.getInitialized());
        setEmail(newStudent.getEmail());
        setTel(newStudent.getTel());
        setMajor(newStudent.getMajor());
        setInstitute(newStudent.getInstitute());
        setStatus(newStudent.getStatus());
    }
}

// 学生实体与专业、学院实体存在耦合