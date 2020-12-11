package com.example.spring_security_demo.services;


import com.example.spring_security_demo.common.ConstantsClass;
import com.groupdocs.watermark.Watermark;
import com.groupdocs.watermark.internal.c.a.s.internal.m0.Ob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import com.spire.pdf.*;
import java.awt.geom.*;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WaterMarkPdfGeneration {


    public void manipulatePdf() throws Exception {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(ConstantsClass.INPUT_OUTPUT_FILE_DIRECTORY+"ClassEightAdmitCards.pdf");

        for (Object object : pdf.getPages()) {
            PdfPageBase page = (PdfPageBase) object;
            page.setBackgroundImage(ConstantsClass.AMAR_AMI_LOGO);
            Rectangle2D rect = new Rectangle2D.Float();
            rect.setFrame(130, 50, 360, 360);
            page.setBackgroundRegion(rect);
        }

        pdf.saveToFile(ConstantsClass.INPUT_OUTPUT_FILE_DIRECTORY+"ImageWatermark.pdf");
        pdf.close();

    }
}
