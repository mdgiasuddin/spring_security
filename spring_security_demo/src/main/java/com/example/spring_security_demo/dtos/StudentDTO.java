package com.example.spring_security_demo.dtos;

import com.example.spring_security_demo.datasource.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String name;
    private String schoolName;
    private String classId;
    private String schoolRollNo;
    private String rollNo;
    private String regNo;

    public StudentDTO(Student student) {
        this.id = String.valueOf(student.getId());
        this.name = student.getName();
        this.schoolName = student.getSchoolName();
        this.classId = student.getClassId();
        this.schoolRollNo = String.valueOf(student.getSchoolRollNo());
        this.rollNo = String.valueOf(student.getRollNo());
        this.regNo = String.valueOf(student.getRegNo());
    }

}
