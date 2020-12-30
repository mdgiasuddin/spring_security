package com.example.spring_security_demo.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class DefaultHeaderFooter extends PdfPageEventHelper {

    Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

    public void onStartPage(PdfWriter writer, Document document) {

        float top = document.getPageSize().getTop();
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Phone: 8813483, 8814375, 8813126", footerFont), 300, top - 20, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Fax: 880-2-9884446; G.P.O. Box No. 3381, Dhaka", footerFont), 300, top - 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("E-mail: info@thecitybank.com; Web: www.thecitybank.com; SWIFT: CIBLBDDH", footerFont), 300, top - 40, 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Phone: 8813483, 8814375, 8813126", footerFont), 50, 50, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Fax: 880-2-9884446; G.P.O. Box No. 3381, Dhaka", footerFont), 50, 40, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("E-mail: info@thecitybank.com; Web: www.thecitybank.com; SWIFT: CIBLBDDH", footerFont), 50, 30, 0);
    }
}
