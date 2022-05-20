package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_table")
public class TimeTable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "num", nullable = false, length = 45)
    private String num;     // 1-13中的某个数字
    @Column(name = "starttime", nullable = false, length = 45)
    private String starttime;    // 比如08:00
    @Column(name = "endtime", nullable = false, length = 45)
    private String endtime;      // 比如08:45
}
