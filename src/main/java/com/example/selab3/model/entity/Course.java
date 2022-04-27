package com.example.selab3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "coursenum", nullable = false, length = 45)
    private String coursenum;   // 课程编号，当前的逻辑只要不重复即可。后续可能根据需求作进一步要求
    @Column(name = "coursehour", nullable = false, length = 45)
    private String coursehour;  // 学时
    @Column(name = "credit", nullable = false, length = 45)
    private String credit;  // 学分
    @Column(name = "capacity", nullable = false, length = 5)
    private String capacity;    // 选课人数上限
    @Lob
    @Column(name = "intro", nullable = false, length = 65535)
    private String intro;


    public void update(Course newCourse){
        this.name=newCourse.getName();
        this.coursenum= newCourse.getCoursenum();
        this.capacity= newCourse.getCapacity();
        this.credit=newCourse.getCredit();
        this.intro=newCourse.getIntro();
        this.coursehour= newCourse.getCoursehour();
    }
}
