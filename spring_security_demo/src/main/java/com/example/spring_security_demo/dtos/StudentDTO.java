package com.example.spring_security_demo.dtos;

import lombok.Data;

@Data
public class StudentDTO {
    private String studentName;
    private String schoolName;
    private String classId;
    private int schoolRollNo;
}
