package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.services.JasperPdfService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity generateJasperReport(@PathVariable String format) throws JRException, FileNotFoundException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=student-report.pdf");
        return new ResponseEntity<>(jasperPdfService.generateJasperReport(format), httpHeaders, HttpStatus.OK);
    }
}
