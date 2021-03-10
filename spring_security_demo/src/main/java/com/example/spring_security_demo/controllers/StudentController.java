package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/new")
    public Object saveStudent(@RequestParam("file") final MultipartFile multipartFile) {
        return studentService.saveStudent(multipartFile);
    }

    @GetMapping("/admit-card/{classId}")
    public void generateAdmitCard(@PathVariable("classId") String classId) throws Exception {
        studentService.generateAdmitCard(classId);
    }

    @PostMapping("/filter/{pageNum}")
    public Page<Student> filterStudentBySearch(@PathVariable("pageNum") int pageNum, @RequestBody Map map) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return studentService.filterStudentBySearch(map, pageable);
    }

}
