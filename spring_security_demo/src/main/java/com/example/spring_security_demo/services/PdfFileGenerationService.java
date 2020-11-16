package com.example.spring_security_demo.services;


import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.utils.PdfFormattingUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfFileGenerationService {

    private final PdfFormattingUtils pdfFormattingUtils;


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
}
