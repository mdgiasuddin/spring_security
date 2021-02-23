package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.services.ExcelGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/excel/generation")
public class ExcelGenerationController {

    private final ExcelGenerationService excelGenerationService;

    @GetMapping("/test")
    public Object generateTestExcel() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=test.xlsx");
        return new ResponseEntity<>(excelGenerationService.createFile(), httpHeaders, HttpStatus.OK);
    }
}
