package com.example.spring_security_demo.services;

import com.example.spring_security_demo.dtos.HeaderFooterText;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public class DefaultHeaderFooter extends PdfPageEventHelper {

    List<HeaderFooterText> headerFooterTextList;
    private Font footerFont;
    private Image image;

    public DefaultHeaderFooter(List<HeaderFooterText> headerFooterTextList) {
        this.footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);
        this.headerFooterTextList = headerFooterTextList;
        this.image = null;
    }

    public DefaultHeaderFooter(List<HeaderFooterText> headerFooterTextList, Image image) {
        this.footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);
        this.headerFooterTextList = headerFooterTextList;
        this.image = image;
    }

    public void onEndPage(PdfWriter writer, Document document) {

        if (image != null) {
            try {
                writer.getDirectContent().addImage(image);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        for (HeaderFooterText headerFooterText : this.headerFooterTextList) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(headerFooterText.getText(), this.footerFont)
                    , headerFooterText.getPoint().getX(), headerFooterText.getPoint().getY(), headerFooterText.getPoint().getRotation());
        }
    }
}
