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
import java.time.format.DateTimeFormatter;
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

        cellStyle.setFillForegroundColor(new XSSFColor(color));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }

    private XSSFCellStyle createDateCellStyle(XSSFWorkbook workbook, boolean timeFlag) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        String timePart = timeFlag ? " hh:mm:ss AM/PM" : "";
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd MMM yyyy" + timePart + "")
        );
        return cellStyle;
    }

    public InputStreamResource createExcelFile(ExcelData excelData) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excelData.getSheetName());

        XSSFCellStyle headerCellStyle = createCellStyle(workbook, new Color(146, 190, 238));

        int rowNum = 0, colNum = 0;
        Row headerRow = sheet.createRow(rowNum);
        for (String headerCell : excelData.getHeaderRow()) {
            Cell cell = headerRow.createCell(colNum);
            cell.setCellValue(headerCell);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(colNum);
            colNum++;

        }
        rowNum++;

        CellStyle dateCellStyle = createDateCellStyle(workbook, false);
        CellStyle dateTimeCellStyle = createDateCellStyle(workbook, true);
        for (Object[] otherRow : excelData.getOtherRowList()) {
            Row row = sheet.createRow(rowNum);
            colNum = 0;
            for (Object otherRowCell : otherRow) {
                Cell cell = row.createCell(colNum);

                if (otherRowCell instanceof Integer || otherRowCell instanceof Float || otherRowCell instanceof Double)
                    cell.setCellValue(Double.parseDouble(String.valueOf(otherRowCell)));
                else if (otherRowCell instanceof Boolean)
                    cell.setCellValue((boolean) otherRowCell);
                else if (otherRowCell instanceof LocalDate) {
                    cell.setCellValue((LocalDate) otherRowCell);
                    cell.setCellStyle(dateCellStyle);
                } else if (otherRowCell instanceof LocalDateTime) {
                    cell.setCellValue(((LocalDateTime) otherRowCell));
                    cell.setCellStyle(dateTimeCellStyle);
                } else
                    cell.setCellValue(String.valueOf(otherRowCell));

                colNum++;
            }
            rowNum++;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(inputStream);

    }

    public InputStreamResource createFile() throws IOException {

        String[] headers = new String[]{
                "Sl.", "Name", "Father's Name", "Adult", "Class", "Roll", "Mark", "Float", "Date of Birth", "Current Time", "Date String"
        };

        List<Object[]> otherRowList = new ArrayList<>();
        for (int i = 1; i <= 2000; i++) {
            Object[] otherRow = new Object[]{
                    i + ".", "Gias Uddin", "Md Mosharraf Hossain", true, 10, 2, 50.25, 20.1f, LocalDate.now(), LocalDateTime.now(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            };
            otherRowList.add(otherRow);
        }

        return createExcelFile(new ExcelData("Test", headers, otherRowList));
    }
}
