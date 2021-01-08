package com.example.spring_security_demo.services;

import com.example.spring_security_demo.dtos.HeaderFooterText;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public class DefaultHeaderFooter extends PdfPageEventHelper {

    List<HeaderFooterText> headerFooterTextList;
    private Font footerFont;

    public DefaultHeaderFooter(List<HeaderFooterText> headerFooterTextList) {
        this.footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);
        this.headerFooterTextList = headerFooterTextList;
    }

    public void onEndPage(PdfWriter writer, Document document) {

        for (HeaderFooterText headerFooterText : this.headerFooterTextList) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(headerFooterText.getText(), this.footerFont)
                    , headerFooterText.getPoint().getX(), headerFooterText.getPoint().getY(), headerFooterText.getPoint().getRotation());
        }
    }
}
