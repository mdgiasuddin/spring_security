package com.example.spring_security_demo.datasource;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_table")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String schoolName;

    private Integer schoolRollNo;

    private String classId;

    private Integer rollNo;

    private Integer regNo;

    private String verificationNo;

    private Double marks;
}
