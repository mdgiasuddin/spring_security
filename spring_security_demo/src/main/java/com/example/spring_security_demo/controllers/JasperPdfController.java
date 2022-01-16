package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.services.JasperPdfService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/jasper")
public class JasperPdfController {

    private final JasperPdfService jasperPdfService;

    public JasperPdfController(JasperPdfService jasperPdfService) {
        this.jasperPdfService = jasperPdfService;
    }


    @GetMapping("/student")
    public List<Student> getAllStudent() {
        return jasperPdfService.getAllStudent();
    }

    @GetMapping("/student/report/{format}")
    public String generateJasperReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return jasperPdfService.generateJasperReport(format);
    }
}
