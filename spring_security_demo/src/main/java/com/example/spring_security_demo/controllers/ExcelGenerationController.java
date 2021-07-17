package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.services.ExcelGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/excel/download")
public class ExcelGenerationController {

    private final ExcelGenerationService excelGenerationService;

    @GetMapping(value = "/test/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generateTestExcel(@PathVariable String fileName) throws IOException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=" + fileName + "");
        return new ResponseEntity<>(excelGenerationService.createFile(), httpHeaders, HttpStatus.OK);
    }
}
