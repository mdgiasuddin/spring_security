package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.dtos.StudentDTO;
import com.example.spring_security_demo.services.StudentElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/elastic/student")
public class StudentElasticController {

    @Autowired
    private StudentElasticService studentElasticService;

    @GetMapping("/all")
    public List<StudentDTO> getAllStudent() {
        return studentElasticService.getAllStudent();
    }

    @GetMapping("/to/document")
    public Integer saveToElasticDocument() {
        return studentElasticService.saveToElasticDocument();
    }

    @GetMapping("/all/{name}")
    public List<StudentDTO> getStudentByName(@PathVariable String name) {
        return studentElasticService.getStudentByName(name);
    }
}
