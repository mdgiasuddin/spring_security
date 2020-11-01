package com.example.spring_security_demo.datasource;

import com.example.spring_security_demo.common.BaseAuditingEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_table")
public class Student extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_roll_no")
    private Integer schoolRollNo;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "roll_no")
    private Integer rollNo;

    @Column(name = "reg_no")
    private Integer regNo;

    @Column(name = "verification_no")
    private String verificationNo;

    @Column(name = "marks")
    private Double marks;
}
