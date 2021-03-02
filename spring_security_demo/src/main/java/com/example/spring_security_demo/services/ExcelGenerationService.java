package com.example.spring_security_demo.services;

import com.example.spring_security_demo.dtos.ExcelData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExcelGenerationService {

    private XSSFCellStyle createCellStyle(XSSFWorkbook workbook, Color color) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        XSSFColor xssfColor = new XSSFColor(color);
        cellStyle.setFillForegroundColor(xssfColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }

    private XSSFCellStyle createDateCellStyle(XSSFWorkbook workbook, boolean timeFlag) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        String timePart = timeFlag ? " hh:mm:ss AM/PM" : "";
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd-MMM-yyyy" + timePart + "")
        );
        return cellStyle;
    }

    public Object createExcelFile(ExcelData excelData) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excelData.getSheetName());

        XSSFCellStyle headerCellStyle = createCellStyle(workbook, new Color(230, 200, 200));

        int rowNum = 0, cellNum = 0;
        Row headerRow = sheet.createRow(rowNum);
        for (String headerCell : excelData.getHeaderRow()) {
            Cell cell = headerRow.createCell(cellNum);
            cell.setCellValue(headerCell);
            cell.setCellStyle(headerCellStyle);
            cellNum++;
        }
        rowNum++;

        CellStyle dateCellStyle = createDateCellStyle(workbook, false);
        CellStyle dateTimeCellStyle = createDateCellStyle(workbook, true);
        for (Object[] otherRow : excelData.getOtherRowList()) {
            Row row = sheet.createRow(rowNum);
            cellNum = 0;
            for (Object otherRowCell : otherRow) {
                Cell cell = row.createCell(cellNum);
                if (otherRowCell.getClass().equals(String.class))
                    cell.setCellValue(String.valueOf(otherRowCell));
                else if (otherRowCell.getClass().equals(Integer.class))
                    cell.setCellValue((int) otherRowCell);
                else if (otherRowCell.getClass().equals(Double.class))
                    cell.setCellValue((double) otherRowCell);
                else if (otherRowCell.getClass().equals(Boolean.class))
                    cell.setCellValue((boolean) otherRowCell);
                else if (otherRowCell.getClass().equals(LocalDate.class)) {
                    cell.setCellValue((LocalDate) otherRowCell);
                    cell.setCellStyle(dateCellStyle);
                } else if (otherRowCell.getClass().equals(LocalDateTime.class)) {
                    cell.setCellValue((LocalDateTime) otherRowCell);
                    cell.setCellStyle(dateTimeCellStyle);
                }

                cellNum++;
            }
            rowNum++;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new InputStreamResource(byteArrayInputStream);

    }

    public Object createFile() throws IOException {

        String[] headers = new String[]{
                "Sl.", "Name", "Father's Name", "Adult", "Class", "Roll", "Mark", "Date of Birth", "Current Time"
        };

        List<Object[]> otherRowList = new ArrayList<>();
        for (int i = 1; i <= 2000; i++) {
            Object[] otherRow = new Object[]{
                    i + ".", "Gias Uddin", "Md Mosharraf Hossain", true, 10, 2, 50.25, LocalDate.now(), LocalDateTime.now()
            };
            otherRowList.add(otherRow);
        }

        ExcelData excelData = new ExcelData("Test", headers, otherRowList);
        return createExcelFile(excelData);
    }
}
