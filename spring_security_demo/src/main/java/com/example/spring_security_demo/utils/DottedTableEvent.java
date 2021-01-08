package com.example.spring_security_demo.utils;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

public class DottedTableEvent implements PdfPTableEvent {

    private float unitsOn;
    private float unitsOff;
    private float phase;

    public DottedTableEvent() {
        this.unitsOn = 0.2f;
        this.unitsOff = 2f;
        this.phase = 2f;
    }

    public DottedTableEvent(float unitsOn, float unitsOff, float phase) {
        this.unitsOn = unitsOn;
        this.unitsOff = unitsOff;
        this.phase = phase;
    }

    @Override
    public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {

        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(this.unitsOn, this.unitsOff, this.phase);
        float llx = widths[0][0];
        float urx = widths[0][widths[0].length - 1];
        for (int i = 0; i < heights.length; i++) {
            canvas.moveTo(llx, heights[i]);
            canvas.lineTo(urx, heights[i]);
        }
        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i].length; j++) {
                canvas.moveTo(widths[i][j], heights[i]);
                canvas.lineTo(widths[i][j], heights[i + 1]);
            }
        }
        canvas.stroke();
    }
}
