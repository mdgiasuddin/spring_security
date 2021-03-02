package com.example.spring_security_demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelData {
    private String sheetName;
    private String[] headerRow;
    private List<Object[]> otherRowList;
}
