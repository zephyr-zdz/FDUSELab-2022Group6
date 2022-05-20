package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher", indexes = {
        @Index(name = "Jobnum_UNIQUE", columnList = "jobnum", unique = true),
        @Index(name = "Identity_UNIQUE", columnList = "identity", unique = true)
})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "identity", nullable = false, length = 18)
    private String identity;
    @Column(name = "jobnum", nullable = false, length = 8)
    private String jobnum;
    @Column(name = "password", length = 30, nullable = true)
    private String password;
    @Column(name = "initialized", length = 1, nullable = true)
    private String initialized; // Y/N
    @Column(name = "email", length = 30, nullable = true)
    private String email;
    @Column(name = "tel", length = 15, nullable = true)
    private String tel;
    @Column(name = "major", length = 45, nullable = false)
    private String major;
    @Column(name = "institute", length = 45, nullable = false)
    private String institute;
    @Column(name = "status", nullable = true, length = 1)
    private String status;  // Y/N

    public void update(Teacher newTeacher) {
        setName(newTeacher.getName());
        setIdentity(newTeacher.getIdentity());
        setJobnum(newTeacher.getJobnum());
        setPassword(newTeacher.getPassword());
        setInitialized(newTeacher.getInitialized());
        setEmail(newTeacher.getEmail());
        setTel(newTeacher.getTel());
        setMajor(newTeacher.getMajor());
        setInstitute(newTeacher.getInstitute());
        setStatus(newTeacher.getStatus());
    }
}
// 教师实体与专业、学院实体存在耦合