package com.example.spring_security_demo.utils;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class DottedCellEvent implements PdfPCellEvent {
    private float unitsOn;
    private float unitsOff;
    private float phase;
    private boolean bottomOnly;

    public DottedCellEvent(boolean bottomOnly) {
        this.unitsOn = 0.3f;
        this.unitsOff = 2f;
        phase = 2f;
        this.bottomOnly = bottomOnly;
    }

    public DottedCellEvent() {
        this.unitsOn = 0.3f;
        this.unitsOff = 2f;
        phase = 2f;
        bottomOnly = false;
    }

    public DottedCellEvent(float unitsOn, float unitsOff, float phase, boolean bottomOnly) {
        this.unitsOn = unitsOn;
        this.unitsOff = unitsOff;
        this.phase = phase;
        this.bottomOnly = bottomOnly;
    }

    public DottedCellEvent(float unitsOn, float unitsOff, float phase) {
        this.unitsOn = unitsOn;
        this.unitsOff = unitsOff;
        this.phase = phase;
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(this.unitsOn, this.unitsOff, this.phase);
        if (this.bottomOnly) {
            canvas.moveTo(position.getLeft(), position.getBottom());
            canvas.lineTo(position.getRight(), position.getBottom());
        } else
            canvas.rectangle(position.getLeft(), position.getBottom(), position.getWidth(), position.getHeight());
        canvas.stroke();
    }
}
