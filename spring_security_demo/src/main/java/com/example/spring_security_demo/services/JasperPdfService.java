package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.repositories.StudentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public InputStreamResource generateJasperReport(String format) throws FileNotFoundException, JRException {

        List<Student> studentList = studentRepository.findAll();
        File file = ResourceUtils.getFile("classpath:students_jasper.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);
        Map<String, Object> map = new HashMap<>();
        map.put("createdBy", "Giash Uddin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (format.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "/home/giash/Giash/reports/student_report.html");

            return null;
        } else if (format.equalsIgnoreCase("pdf")) {
            // Report for direct save
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/giash/Giash/reports/student_report.pdf");

            //  Report for manual download
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new InputStreamResource(inputStream);
        }

        return null;

//        return "Jasper report generated successfully!";
    }
}
