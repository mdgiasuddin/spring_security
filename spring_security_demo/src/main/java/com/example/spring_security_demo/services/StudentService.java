package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {
    private final StudentRepository studentRepository;

    public Object saveStudent(Map<String, Object> map) {
        Student student = new Student();

        student.setName(map.get("name").toString());
        student.setSchoolName(map.get("schoolName").toString());
        student.setClassId("Ten");
        student.setRollNo(100);
        student.setRegNo(200);
        student.setMarks(50.0);

        studentRepository.save(student);

        return student;
    }
}
