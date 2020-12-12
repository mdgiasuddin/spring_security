package com.example.spring_security_demo.controllers;


import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.services.PdfFileGenerationService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfFileGenerationController {

    private final PdfFileGenerationService pdfFileGenerationService;

    @GetMapping("/Generate-Pdf-File")
    public ResponseEntity generatePdfFile() throws IOException, DocumentException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=winding-font-example.pdf");
        return new ResponseEntity<>(pdfFileGenerationService.generatePdfFile(), httpHeaders, HttpStatus.OK) ;
    }

}
