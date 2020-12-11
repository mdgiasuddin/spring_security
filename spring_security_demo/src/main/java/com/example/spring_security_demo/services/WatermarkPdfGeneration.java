package com.example.spring_security_demo.services;

import com.example.spring_security_demo.common.ConstantsClass;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WatermarkPdfGeneration {

    public void addWaterMarkToPdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int numberOfPages = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

        Image image = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
        float width = 350;
        float height = 350;

        PdfGState gState = new PdfGState();
        gState.setFillOpacity(0.1f);
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
}
