package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/save-student")
    public Object saveStudent(@RequestBody Map map) {
        return studentService.saveStudent(map);
    }
}
