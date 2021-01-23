package com.example.spring_security_demo.utils;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class CustomBorderEvent implements PdfPCellEvent {
    private boolean left;
    private boolean right;
    private boolean top;
    private boolean bottom;

    public CustomBorderEvent(boolean left, boolean right, boolean top, boolean bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void cellLayout(PdfPCell pdfPCell, Rectangle position, PdfContentByte[] pdfContentBytes) {
        PdfContentByte canvas = pdfContentBytes[PdfPTable.LINECANVAS];
        if (this.left) {
            canvas.moveTo(position.getLeft(), position.getTop());
            canvas.lineTo(position.getLeft(), position.getBottom());
        }
        if (this.right) {
            canvas.moveTo(position.getRight(), position.getTop());
            canvas.lineTo(position.getRight(), position.getBottom());
        }
        if (this.top) {
            canvas.moveTo(position.getLeft(), position.getTop());
            canvas.lineTo(position.getRight(), position.getTop());
        }
        if (this.bottom) {
            canvas.moveTo(position.getLeft(), position.getBottom());
            canvas.lineTo(position.getRight(), position.getBottom());
        }
        canvas.stroke();
    }
}
