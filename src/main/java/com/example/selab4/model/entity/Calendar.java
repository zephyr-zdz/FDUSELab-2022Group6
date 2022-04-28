package com.example.selab4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Calendar")
public class Calendar {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private Integer id;
    @Column(name = "day",nullable = false)
    private String day;
    @Column(name = "number",nullable = false)
    private String number;
}
