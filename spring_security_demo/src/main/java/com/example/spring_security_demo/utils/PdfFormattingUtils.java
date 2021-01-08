package com.example.spring_security_demo.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfFormattingUtils {

    public PdfPCell getDottedLineBorderedCell(String text, int alignment, Font font, PdfPCellEvent dottedCellEvent) {
        PdfPCell cell = new PdfPCell();
        cell.setCellEvent(dottedCellEvent);
        cell.setPhrase(new Phrase(text, font));
        cell.setPadding(2);
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

    public Paragraph createParagraph(String text, Font font, float spacing, int alignment) {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setSpacingAfter(spacing);
        paragraph.setAlignment(alignment);

        return paragraph;
    }

    public Paragraph createParagraphWithLeadingSpace(String text, Font font, int leadingSpace, int alignment) {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setLeading(leadingSpace);
        paragraph.setAlignment(alignment);

        return paragraph;
    }

    public PdfPTable createTable(int numberOfColumn, float widthPercentage, float spacing, int[] widths, int alignment) throws DocumentException {
        PdfPTable table = new PdfPTable(numberOfColumn);
        table.setWidthPercentage(widthPercentage);
        table.setSpacingAfter(spacing);
        table.setWidths(widths);
        table.setHorizontalAlignment(alignment);
        return table;
    }

    public PdfPTable createTableWithData(int numberOfColumn, float widthPercentage, float spacing, int[] widths, int alignment, java.util.List<String> header, java.util.List<java.util.List<String>> otherRows, Font headFont, Font font) throws DocumentException {

        PdfPTable table = createTable(numberOfColumn, widthPercentage, spacing, widths, alignment);

        PdfPCell cell;

        for (String cellString : header) {
            cell = new PdfPCell(new Phrase(cellString, headFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        for (java.util.List<String> stringList : otherRows) {
            for (String cellString : stringList) {
                cell = new PdfPCell(new Phrase(cellString, font));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
        }
        return table;
    }

    public PdfPCell getCellWithBorderOption(String text, int alignment, Font font, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(2);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(border);
        cell.setMinimumHeight(font.getSize() + 2);
        return cell;
    }

    public PdfPCell getCellWithBorderOptionWithFixedSize(String text, int alignment, Font font, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(2);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(border);
        cell.setFixedHeight(12);
        return cell;
    }

    public PdfPCell getCellWithBorderOptionWithParagraph(Paragraph paragraph, int alignment, Font font, int border) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(2);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(border);
        cell.setMinimumHeight(font.getSize() + 2);
        return cell;
    }

    public PdfPCell getBorderedCell(String text, int alignment, Font font) {
        return getCellWithBorderOption(text, alignment, font, PdfPCell.BOX);
    }

    public PdfPCell getBorderlessCell(String text, int alignment, Font font) {
        return getCellWithBorderOption(text, alignment, font, PdfPCell.NO_BORDER);
    }

    public PdfPCell getBorderlessCellWithParagaraph(Paragraph paragraph, int alignment, Font font) {
        return getCellWithBorderOptionWithParagraph(paragraph, alignment, font, PdfPCell.NO_BORDER);
    }

    public PdfPCell getBorderedCellWithParagaraph(Paragraph paragraph, int alignment, Font font) {
        return getCellWithBorderOptionWithParagraph(paragraph, alignment, font, PdfPCell.BOX);
    }


    public PdfPCell getCellWithBorderOption(Paragraph paragraph, int border) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setPadding(3);
        cell.setBorder(border);
        return cell;
    }

    public PdfPCell getCellWithBorderOptionWithoutpadding(Paragraph paragraph, int border) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setPadding(-3);
        cell.setBorder(border);
        return cell;
    }

    public PdfPCell getBorderlessCell(Paragraph paragraph) {
        return getCellWithBorderOption(paragraph, PdfPCell.NO_BORDER);
    }

    public PdfPCell getBorderlessCellWithoutPadding(Paragraph paragraph) {
        return getCellWithBorderOptionWithoutpadding(paragraph, PdfPCell.NO_BORDER);
    }

    public PdfPCell getBorderedCell(Paragraph paragraph) {
        return getCellWithBorderOption(paragraph, PdfPCell.BOX);
    }

    public PdfPCell getBorderedCell(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(table);
        cell.setPadding(3);
        cell.setBorder(PdfPCell.BOX);
        return cell;
    }

    public PdfPCell getCellWithBorderOption(java.util.List<Paragraph> paragraphList, int border) {
        PdfPCell cell = new PdfPCell();
        for (Paragraph paragraph : paragraphList)
            cell.addElement(paragraph);

        cell.setPadding(3);
        cell.setBorder(border);
        return cell;
    }

    public PdfPCell getBorderedCellWithTable(java.util.List<PdfPTable> tableList) {
        PdfPCell cell = new PdfPCell();
        for (PdfPTable table : tableList)
            cell.addElement(table);

        cell.setPadding(3);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }


    public PdfPCell getBorderedCell(java.util.List<Paragraph> paragraphList) {
        return getCellWithBorderOption(paragraphList, PdfPCell.BOX);
    }

    public PdfPCell getBorderlessCell(List<Paragraph> paragraphList) {
        return getCellWithBorderOption(paragraphList, PdfPCell.NO_BORDER);
    }

    public PdfPCell createMergedCellWithAlignment(String content, int colSpan, int rowSpan, int border, Font font, float minimumHeight, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setBorder(border);
        cell.setHorizontalAlignment(alignment);
        cell.setMinimumHeight(minimumHeight);
        return cell;
    }

    public PdfPCell getFormatedPdfCell(String content, Font font, int alingment, int leadingSpace, int border) {
        Paragraph paragraph = createParagraphWithLeadingSpace(content, font, leadingSpace, alingment);
        PdfPCell textCell = getCellWithBorderOption(paragraph, border);
        return textCell;
    }

    public PdfPCell getFormatedPdfCellWithSpans(String content, Font font, int alingment, int rowSpan, int colSpan, int leadingSpace, int border) {
        Paragraph paragraph = createParagraphWithLeadingSpace(content, font, leadingSpace, alingment);
        PdfPCell textCell = getCellWithBorderOption(paragraph, border);
        textCell.setColspan(colSpan);
        textCell.setRowspan(rowSpan);
        return textCell;
    }
}
