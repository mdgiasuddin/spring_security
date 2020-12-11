package com.example.spring_security_demo.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


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

    public int getIntegerFromAllCellType(Cell cell) {
        if (cell == null)
            return -1;
        if (cell.getCellType().equals(CellType.NUMERIC))
            return (int) cell.getNumericCellValue();
        if (cell.getCellType().equals(CellType.STRING))
            return Integer.parseInt(cell.getStringCellValue());

        return -1;
    }
}
