package com.example.spring_security_demo.services;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WatermarkPdfGeneration {

    public void addWaterMarkToPdf(String src, String dest, Image image, float width, float height, float opacity) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int numberOfPages = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

        PdfGState gState = new PdfGState();
        gState.setFillOpacity(opacity);
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        for (int i = 1; i <= numberOfPages; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gState);
            over.addImage(image, width, 0, 0, height, x - (width / 2), y - (height / 2));
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    public InputStreamResource addWaterMarkToPdf(InputStream inputStream, Image image, Rectangle pageSize, float width, float height, float opacity) throws IOException, DocumentException {
        float x = (pageSize.getLeft() + pageSize.getRight() - width) / 2;
        float y = (pageSize.getTop() + pageSize.getBottom() - height) / 2;

        return addWaterMarkToPdf(inputStream, image, x, y, width, height, opacity);
    }

    public InputStreamResource addWaterMarkToPdf(InputStream inputStream, Image image, float x, float y, float width, float height, float opacity) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputStream);
        ByteArrayOutputStream waterMarkOutputStream = new ByteArrayOutputStream();
        int numberOfPages = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader, waterMarkOutputStream);

        PdfGState gState = new PdfGState();
        gState.setFillOpacity(opacity);
        PdfContentByte over;
        for (int i = 1; i <= numberOfPages; i++) {
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gState);
            over.addImage(image, width, 0, 0, height, x, y);
            over.restoreState();
        }
        stamper.close();
        reader.close();

        ByteArrayInputStream watermarkInputStream = new ByteArrayInputStream(waterMarkOutputStream.toByteArray());

        return new InputStreamResource(watermarkInputStream);
    }
}
