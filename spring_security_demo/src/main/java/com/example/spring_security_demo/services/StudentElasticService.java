package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.dtos.StudentDTO;
import com.example.spring_security_demo.dtos.StudentDTOAll;
import com.example.spring_security_demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentElasticService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<StudentDTO> getAllStudent() {
        List<Student> studentList = studentRepository.findAll();

        List<StudentDTO> studentDTOList = studentList.stream().map(student -> new StudentDTO(student))
                .collect(Collectors.toList());

        return studentDTOList;
    }

    public Integer saveToElasticDocument() {
        List<StudentDTO> studentDTOList = getAllStudent();

        Integer count = restTemplate.postForObject("http://localhost:8301/api/student/new", studentDTOList, Integer.class);
        return count;

    }

    public List<StudentDTO> getStudentByName(String name) {
        List<StudentDTO> studentDTOList = getAllStudent();

        StudentDTOAll studentDTOAll = restTemplate.getForObject("http://localhost:8301/api/student/all/" + name, StudentDTOAll.class);
        return studentDTOAll.getStudentList();

    }
}
