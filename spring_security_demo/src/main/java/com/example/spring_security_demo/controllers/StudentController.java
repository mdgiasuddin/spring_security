package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/save-student/add")
    public Object saveStudent(@RequestParam("file") final MultipartFile multipartFile) {
        return studentService.saveStudent(multipartFile);
    }

    @GetMapping("/generate-admit-card/{classId}")
    public void generateAdmitCard(@PathVariable("classId") String classId) throws Exception {
        studentService.generateAdmitCard(classId);
    }

}
