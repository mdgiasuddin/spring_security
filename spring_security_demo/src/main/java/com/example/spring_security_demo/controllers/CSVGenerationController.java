package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.services.CSVGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/csv/generation")
public class CSVGenerationController {

    private final CSVGenerationService csvGenerationService;

    @GetMapping("/readfile")
    public void readFile() {
        csvGenerationService.readFile();
    }

    @PostMapping("/readfile/upload")
    public void readFileFromUpload(@RequestParam("file") final MultipartFile multipartFile) {
        csvGenerationService.readFileFromUpload(multipartFile);
    }

    @GetMapping("/createfile")
    public Object createFile() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=test.csv");
        return new ResponseEntity<>(csvGenerationService.createFile(), httpHeaders, HttpStatus.OK);
    }

}
