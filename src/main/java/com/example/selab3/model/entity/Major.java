package com.example.selab3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "major")
public class Major {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "institute", length = 45, nullable = false)
    private String institute;

    public Major(String name) {
        this.name=name;
    }

    public void update(Major newMajor) {
        setName(newMajor.getName());
        setInstitute(newMajor.getInstitute());
    }
}
