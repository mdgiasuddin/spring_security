package com.example.spring_security_demo.utils;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class DottedCell implements PdfPCellEvent {
    @Override
    public void cellLayout(PdfPCell cell, Rectangle position,
                           PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(0.5f, 2f, 3f);
        canvas.rectangle(position.getLeft(), position.getBottom(),
                position.getWidth(), position.getHeight());
        canvas.stroke();
    }
}
