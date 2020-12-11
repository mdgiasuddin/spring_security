package com.example.spring_security_demo.services;


import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.utils.PdfFormattingUtils;
import com.itextpdf.layout.Canvas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfFileGenerationService {

    private final PdfFormattingUtils pdfFormattingUtils;

    private final int SPACING = 20;


    public Object generatePdfFile() throws IOException, DocumentException {

        float margin = 50;

        Document document = new Document(PageSize.A4, margin, margin, margin, margin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.NORMAL, BaseColor.BLACK);


        BaseFont baseFont = BaseFont.createFont( ConstantsClass.STATIC_RESOURCES_DIRECTORY + "wingding.ttf", BaseFont.IDENTITY_H, false);
        Font windingFont = new Font(baseFont, 11f, Font.NORMAL);


        Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.BLACK);
        Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC, BaseColor.BLACK);

        float spacing = 20;
        float largeSpacing = 40;
        float narrowSpacing = 10;
        float zeroSpacing = 0;

        float minimumHeightNormal = 12f;
        float minimumHeightLarge = 30f;

        try {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            char checked = '\u00FE';
            char unchecked='\u00A8';
            char like = '\u0043';
            char disLike = '\u0044';
            char list = '\u0076';
            char circle = '\u006C';

            PdfPTable tableChecked = new PdfPTable(6);
            tableChecked.setWidthPercentage(90);
            tableChecked.setSpacingAfter(narrowSpacing);
            tableChecked.setWidths(new int[] {1, 1, 1, 1, 1, 1});

            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(checked), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(like), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(unchecked), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(disLike), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(list), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(circle), Element.ALIGN_LEFT, windingFont));
            document.open();

            document.add(tableChecked);


            document.close();

            System.out.println("Successful");

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(byteArrayInputStream);
    }


    public void generateAdmitCard(List<Student> studentList, String filename) throws IOException, DocumentException {

        Rectangle pageSize = new Rectangle(594, 423);
        pageSize.setBackgroundColor(new BaseColor(230, 230, 250));
        float margin = 25;
        Document document = new Document(pageSize, margin, margin, margin, margin);

        BaseFont scriptMTBold = BaseFont.createFont(ConstantsClass.SCRIPT_MT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont oldEnglish = BaseFont.createFont(ConstantsClass.OLD_ENGLISH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(Font.FontFamily.TIMES_ROMAN,10f, Font.ITALIC, BaseColor.BLACK);

        Font oldEnglish25 = new Font(oldEnglish, 25, Font.NORMAL, BaseColor.BLACK);
        Font oldEnglishIT18 = new Font(oldEnglish, 18, Font.ITALIC, BaseColor.BLACK);
        Font scriptMTBold11 = new Font(scriptMTBold, 11, Font.NORMAL, BaseColor.BLACK);


        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        Paragraph paragraphDummy = new Paragraph("Examinee must bring this card to the examination hall.", font);
        document.add(paragraphDummy);
        document.newPage();


        Image logoImage = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
        Image signImage = Image.getInstance(ConstantsClass.SIGNATURE_IMAGE);

        for (Student student : studentList) {

            logoImage.setAlignment(Element.ALIGN_LEFT);
            logoImage.setBorderWidth(SPACING);


            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setWidths(new int[]{1, 5});

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(logoImage);
            imageCell.setBorder(PdfPCell.NO_BORDER);
            imageTable.addCell(imageCell);


            Font font2 = new Font(oldEnglish, 12, Font.NORMAL, BaseColor.BLACK);
            PdfPCell textCell = new PdfPCell();

            Paragraph paragraph = new Paragraph("Amar Ami\n", oldEnglish25);
            paragraph.add(new Chunk("Talent Evaluation Exam - 2020\n", font2));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(paragraph);
            textCell.setBorder(Rectangle.NO_BORDER);


            imageTable.addCell(textCell);
            imageTable.setSpacingAfter(0);

            Paragraph paragraph1 = new Paragraph("Admit Card", oldEnglishIT18);
            paragraph1.setSpacingAfter(30);
            paragraph1.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{5, 3});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Name: " + student.getName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No: " + student.getRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School: " + student.getSchoolName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Registration No: " + student.getRegNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class: " + student.getClassId(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class Roll: " + student.getSchoolRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Centre: Betbaria Secondary School", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Exam Date: 09 Aug, 9.00 am", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            table.setSpacingAfter(30);


            signImage.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidths(new int[]{17, 2, 3});
            table2.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPCell signImageCell = new PdfPCell();
            signImageCell.addElement(signImage);
            signImageCell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(signImageCell);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(new int[]{8, 3});
            table3.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            cell = new PdfPCell(new Phrase("Controller of Exam", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);


            Paragraph line = new Paragraph(new Chunk(new LineSeparator(0.0F, 100.0F, BaseColor.BLACK, Element.ALIGN_LEFT, 1)));

            Paragraph paragraph2 = new Paragraph("Examinee must bring this card to the examination hall.", font);
            Paragraph paragraph3 = new Paragraph("Examinee can keep nothing with him/her in the exam hall except pen, pencil, geometry instruments & calculator.", font);

            document.add(imageTable);
            document.add(paragraph1);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(line);
            document.add(paragraph2);
            document.add(paragraph3);

            document.newPage();
        }

        document.close();
    }





}
