package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.repositories.StudentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperPdfService {

    private final StudentRepository studentRepository;

    @Autowired
    public JasperPdfService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public String generateJasperReport(String format) throws FileNotFoundException, JRException {

        List<Student> studentList = studentRepository.findAll();
        File file = ResourceUtils.getFile("classpath:students_jasper.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);
        Map<String, Object> map = new HashMap<>();
        map.put("createdBy", "Giash Uddin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);

        if (format.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "/home/giash/Giash/reports/student_report.html");
        } else if (format.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/giash/Giash/reports/student_report.pdf");
        }

        return "Jasper report generated successfully!";
    }
}
