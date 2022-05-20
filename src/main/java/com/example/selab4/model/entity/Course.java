package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.lang.Integer.parseInt;

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
    @Column(name = "coursetemplateid", nullable = false)
    private Integer coursetemplateid;
    @Column(name = "coursehour", nullable = false, length = 45)
    private String coursehour;  // 学时
    @Column(name = "credit", nullable = false, length = 45)
    private String credit;  // 学分
    @Column(name = "capacity", nullable = false, length = 5)
    private String capacity;    // 选课人数上限
    @Column(name = "currentcount", nullable = false, length = 5)
    private String currentcount;    // 当前选课人数
    @Lob
    @Column(name = "intro", nullable = false, length = 65535)
    private String intro;
    @Column(name = "ispublic", nullable = false, length = 1)
    private String ispublic;    // Y：所有专业可选（通选课程）；N：部分专业可选（包括只有1个专业可选的情况）
    @Column(name = "semester", nullable = false, length = 10)
    private String semester;     // 学年学期，例如：2021-2022春


    public void update(Course newCourse){
        this.coursetemplateid = newCourse.getCoursetemplateid();
        this.capacity = newCourse.getCapacity();
        this.credit = newCourse.getCredit();
        this.intro = newCourse.getIntro();
        this.coursehour = newCourse.getCoursehour();
        this.ispublic = newCourse.getIspublic();
        this.semester = newCourse.getSemester();
    }

    public void addStudent() {
        setCurrentcount(Integer.toString(parseInt(getCurrentcount()) + 1));
    }

    public void reduceStudent() {
        setCurrentcount(Integer.toString(parseInt(getCurrentcount()) - 1));
    }
}
