package com.example.spring_security_demo.utils;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.stereotype.Component;

@Component
public class PdfFormattingUtils {

    public PdfPCell getBorderlessCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public PdfPCell createMergedCell(String content, int colSpan, int rowSpan, int border, Font font, float minimumHeight) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setMinimumHeight(minimumHeight);
        return cell;
    }
}
