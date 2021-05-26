package com.example.spring_security_demo.dtos;

import com.example.spring_security_demo.datasource.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String schoolName;
    private String classId;
    private Long schoolRollNo;
    private Long rollNo;
    private Long regNo;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.schoolName = student.getSchoolName();
        this.classId = student.getClassId();
        this.schoolRollNo = student.getSchoolRollNo();
        this.rollNo = student.getRollNo();
        this.regNo = student.getRegNo();
    }

}
