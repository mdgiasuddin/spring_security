package com.example.spring_security_demo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.awt.Color;


@Component
public class ExcelFormattingUtils {

    public String getStringFromAllCellType(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            String string = String.valueOf(cell.getNumericCellValue()).trim();
            if (string.endsWith(".0"))
                return string.substring(0, string.length() - 2);
            return string;
        }
        if (cell.getCellType().equals(CellType.STRING))
            return String.valueOf(cell.getStringCellValue()).trim();
        if (cell.getCellType().equals(CellType.BOOLEAN))
            return String.valueOf(cell.getBooleanCellValue()).trim();
        if (cell.getCellType().equals(CellType.ERROR))
            return String.valueOf(cell.getErrorCellValue()).trim();
        if (cell.getCellType().equals(CellType.FORMULA))
            return String.valueOf(cell.getArrayFormulaRange()).trim();
        if (cell.getCellType().equals(CellType.BLANK))
            return "";

        return "";
    }

    public Integer getIntegerFromAllCellType(Cell cell) {
        if (cell == null)
            return -1;
        if (cell.getCellType().equals(CellType.NUMERIC))
            return (int) cell.getNumericCellValue();
        if (cell.getCellType().equals(CellType.STRING))
            return Integer.parseInt(cell.getStringCellValue());

        return -1;
    }

    public CellStyle createCellStyle(XSSFWorkbook workbook, Color color) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        XSSFColor xssfColor = new XSSFColor(color);
        cellStyle.setFillForegroundColor(xssfColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }
}
