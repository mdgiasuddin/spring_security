package com.example.spring_security_demo.services;


import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.dtos.HeaderFooterText;
import com.example.spring_security_demo.dtos.Text2DPoint;
import com.example.spring_security_demo.utils.CustomBorderEvent;
import com.example.spring_security_demo.utils.DottedCellEvent;
import com.example.spring_security_demo.utils.DottedTableEvent;
import com.example.spring_security_demo.utils.PdfFormattingUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfFileGenerationService {

    private final PdfFormattingUtils pdfFormattingUtils;
    private final WatermarkPdfGeneration watermarkPdfGeneration;
    private final MiscellaneousService miscellaneousService;

    private final int SPACING = 20;
    private final int NARROW_SPACING = 5;


    public Object generatePdfFile() throws IOException, DocumentException {

        float margin = 30;

        Document document = new Document(PageSize.A4, margin, margin, 100, margin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.NORMAL, BaseColor.BLACK);

        BaseFont baseFont = BaseFont.createFont( ConstantsClass.STATIC_RESOURCES_DIRECTORY + "wingding.ttf", BaseFont.IDENTITY_H, false);
        Font windingFont = new Font(baseFont, 11f, Font.NORMAL);

        Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.BLACK);
        Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC, BaseColor.BLACK);

        float spacing = 20;
        float largeSpacing = 40;
        float narrowSpacing = 10;
        float zeroSpacing = 0;

        float minimumHeightNormal = 12f;
        float minimumHeightLarge = 30f;

        try {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            float top = document.getPageSize().getTop();
            Image image = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
            image.scaleToFit(80, 80);
            image.setAbsolutePosition(300, top - 90);
            DefaultHeaderFooter defaultHeaderFooter = new DefaultHeaderFooter(
                    Arrays.asList(
                            new HeaderFooterText("Phone: 8813483, 8814375, 8813126", new Text2DPoint(50, top - 10)),
                            new HeaderFooterText("Fax: 880-2-9884446; G.P.O. Box No. 3381, Dhaka", new Text2DPoint(50, top - 20)),
                            new HeaderFooterText("E-mail: info@thecitybank.com; Web: www.thecitybank.com; SWIFT: CIBLBDDH", new Text2DPoint(50, top - 30)),
                            new HeaderFooterText("E-mail: info@thecitybank.com; Web: www.thecitybank.com; SWIFT: CIBLBDDH", new Text2DPoint(50, 10)),
                            new HeaderFooterText("Fax: 880-2-9884446; G.P.O. Box No. 3381, Dhaka", new Text2DPoint(50, 20)),
                            new HeaderFooterText("Phone: 8813483, 8814375, 8813126", new Text2DPoint(50, 30))
                    )
//                    , image
            );
            writer.setPageEvent(defaultHeaderFooter);

            char checked = '\u00FE';
            char unchecked = '\u00A8';
            char like = '\u0043';
            char disLike = '\u0044';
            char list = '\u0076';
            char circle = '\u006C';


            PdfPTable tableChecked = new PdfPTable(6);
            tableChecked.setWidthPercentage(90);
            tableChecked.setSpacingAfter(narrowSpacing);
            tableChecked.setWidths(new int[]{1, 1, 1, 1, 1, 1});

            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(checked), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(like), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(unchecked), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(disLike), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(list), Element.ALIGN_LEFT, windingFont));
            tableChecked.addCell(pdfFormattingUtils.getBorderlessCell(String.valueOf(circle), Element.ALIGN_LEFT, windingFont));
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setSpacingAfter(narrowSpacing);
            table.setWidths(new int[]{1, 2});
            table.setHeaderRows(1);

            table.addCell(new PdfPCell(new Phrase("Sl. ", headFont)));
            table.addCell(new PdfPCell(new Phrase("Name ", headFont)));

            for (int i = 1; i <= 200; i++) {
                table.addCell(pdfFormattingUtils.getBorderedCell(i + ".", Element.ALIGN_CENTER, font));
                table.addCell(pdfFormattingUtils.getBorderedCell("Gias", Element.ALIGN_CENTER, font));
            }

            PdfPTable dottedTable = pdfFormattingUtils.createTable(4, 100, 20, new int[]{8, 8, 8, 76}, Element.ALIGN_CENTER);
            String number = "12345.67";

            DottedCellEvent dottedCellEvent = new DottedCellEvent(true);
            dottedTable.addCell(pdfFormattingUtils.getBorderlessCell("Amount", Element.ALIGN_LEFT, font));
            dottedTable.addCell(pdfFormattingUtils.getDottedLineBorderedCell(number, Element.ALIGN_CENTER, font, dottedCellEvent));
            dottedTable.addCell(pdfFormattingUtils.getBorderlessCell("In Word", Element.ALIGN_CENTER, font));
            dottedTable.addCell(pdfFormattingUtils.getDottedLineBorderedCell(miscellaneousService.numberToWord(number), Element.ALIGN_CENTER, font, dottedCellEvent));

            PdfPTable dottedTable2 = pdfFormattingUtils.createTable(4, 100, 20, new int[]{8, 8, 8, 76}, Element.ALIGN_CENTER);
            number = "12345.67";

            DottedCellEvent dottedCellEvent2 = new DottedCellEvent(2, 2, 2);
            dottedTable2.addCell(pdfFormattingUtils.getBorderlessCell("Amount", Element.ALIGN_LEFT, font));
            dottedTable2.addCell(pdfFormattingUtils.getDottedLineBorderedCell(number, Element.ALIGN_CENTER, font, dottedCellEvent2));
            dottedTable2.addCell(pdfFormattingUtils.getBorderlessCell("In Word", Element.ALIGN_CENTER, font));
            dottedTable2.addCell(pdfFormattingUtils.getDottedLineBorderedCell(miscellaneousService.numberToWord(number), Element.ALIGN_CENTER, font, dottedCellEvent2));

            DottedTableEvent dottedTableEvent = new DottedTableEvent(2f, 2f, 2f);
            PdfPTable dottedTable3 = pdfFormattingUtils.createTable(4, 100, 20, new int[]{8, 8, 8, 76}, Element.ALIGN_CENTER);
            dottedTable3.setTableEvent(dottedTableEvent);
            number = "12345.67";

            dottedTable3.addCell(pdfFormattingUtils.getBorderlessCell("Amount", Element.ALIGN_LEFT, font));
            dottedTable3.addCell(pdfFormattingUtils.getBorderlessCell(number, Element.ALIGN_CENTER, font));
            dottedTable3.addCell(pdfFormattingUtils.getBorderlessCell("In Word", Element.ALIGN_CENTER, font));
            dottedTable3.addCell(pdfFormattingUtils.getBorderlessCell(miscellaneousService.numberToWord(number), Element.ALIGN_CENTER, font));

            document.add(tableChecked);
            document.add(table);
            document.add(dottedTable);
            document.add(dottedTable2);
            document.add(dottedTable3);


            document.close();

            System.out.println("Successful");

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        Image image = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
        float width = 50;
        float height = 50;

        Rectangle pageSize = document.getPageSize();
        //return watermarkPdfGeneration.addWaterMarkToPdf(inputStream, image, 50, pageSize.getTop() - 50, width, height, 1f);
        //return watermarkPdfGeneration.addWaterMarkToPdf(inputStream, image, pageSize, width, height, 1f);

        return watermarkPdfGeneration.addPageNumberToEveryPage(inputStream);
    }

    public Object bcsApplicantsCopy() throws IOException, DocumentException {

        float margin = 25f;

        Document document = new Document(PageSize.A4, margin, margin, margin, margin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.BOLD, BaseColor.BLACK);
        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 9f, Font.NORMAL, BaseColor.BLACK);
        Font verySmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 7f, Font.NORMAL, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.NORMAL, BaseColor.BLACK);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

        BaseFont baseFont = BaseFont.createFont(ConstantsClass.STATIC_RESOURCES_DIRECTORY + "wingding.ttf", BaseFont.IDENTITY_H, false);
        Font windingFont = new Font(baseFont, 11f, Font.NORMAL);


        try {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            float top = document.getPageSize().getTop();
            Image image = Image.getInstance(ConstantsClass.GOVT_SEAL);
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(55, 55);

            PdfPTable imageTable = pdfFormattingUtils.createTable(3, 100, SPACING, new int[]{15, 55, 30}, Element.ALIGN_LEFT);

            CustomBorderEvent leftTopBottom = new CustomBorderEvent(true, false, true, true);
            CustomBorderEvent topBottom = new CustomBorderEvent(false, false, true, true);
            CustomBorderEvent rightTopBottom = new CustomBorderEvent(false, true, true, true);

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(image);
            imageCell.setBorder(PdfPCell.NO_BORDER);
            imageCell.setCellEvent(leftTopBottom);
            imageTable.addCell(imageCell);

            PdfPCell textCell = new PdfPCell();
            Paragraph paragraph = new Paragraph("Government of the People's Republic of Bangladesh\n", smallFont);
            paragraph.add(new Chunk("Bangladesh Public Service Commission\n", boldFont));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            textCell.addElement(paragraph);
            textCell.setBorder(PdfPCell.NO_BORDER);
            textCell.setCellEvent(topBottom);

            PdfPCell textCell2 = new PdfPCell();
            Paragraph paragraph2 = new Paragraph("43rd BCS\n", boldFont);
            paragraph2.add(new Chunk("Examination 2020\n", boldFont));
            paragraph2.add(new Chunk("Applicant's Copy [BPSC Form-1]\n", smallFont));
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            textCell2.addElement(paragraph2);
            textCell2.setBorder(PdfPCell.NO_BORDER);
            textCell2.setCellEvent(rightTopBottom);

            imageTable.addCell(textCell);
            imageTable.addCell(textCell2);

            PdfPTable userIdTable = pdfFormattingUtils.createTable(2, 100, NARROW_SPACING, new int[]{25, 75}, Element.ALIGN_LEFT);
            PdfPCell userIdCell = pdfFormattingUtils.getCellWithBackgroundColor("User ID: CSMKMLVO", boldFont, 0, Element.ALIGN_LEFT, 160, 160, 160);

            Paragraph examCenterParagraph = pdfFormattingUtils.createParagraph("Exam Centre: DHAKA", verySmallFont, 0, Element.ALIGN_RIGHT);
            Paragraph generalParagraph = pdfFormattingUtils.createParagraph("General Cadre", verySmallFont, 0, Element.ALIGN_RIGHT);

            paragraph2.setAlignment(Element.ALIGN_RIGHT);
            PdfPCell examCenterCell = pdfFormattingUtils.getCellWithBackgroundColor(Arrays.asList(examCenterParagraph, generalParagraph), 192, 192, 192);

            userIdTable.addCell(userIdCell);
            userIdTable.addCell(examCenterCell);

            PdfPTable applicantsDetails = pdfFormattingUtils.createTable(4, 100, NARROW_SPACING, new int[]{75, 82, 1, 142}, Element.ALIGN_LEFT);
            Image photoImage = Image.getInstance(ConstantsClass.GIAS_PHOTO);
            photoImage.setAlignment(Element.ALIGN_CENTER);
            photoImage.scaleToFit(110, 110);

            PdfPCell photoImageCell = pdfFormattingUtils.createMergedCell(photoImage, 1, 16, PdfPCell.NO_BORDER);
            applicantsDetails.addCell(photoImageCell);

            PdfPCell emptyColumn = pdfFormattingUtils.getCellWithBackgroundColor("", smallFont, 0, Element.ALIGN_LEFT, 255, 255, 255);
            PdfPCell emptyRow = pdfFormattingUtils.getCellWithBackgroundColor(3, 1, 255, 255, 255);

            PdfPCell detailsCell11 = pdfFormattingUtils.getCellWithBackgroundColor("Applicant's Name", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell12 = pdfFormattingUtils.getCellWithBackgroundColor("MD- GIASH UDDIN", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell21 = pdfFormattingUtils.getCellWithBackgroundColor("Father's Name", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell22 = pdfFormattingUtils.getCellWithBackgroundColor("MD- MOSHARAF HOSSAIN", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell31 = pdfFormattingUtils.getCellWithBackgroundColor("Mother's Name", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell32 = pdfFormattingUtils.getCellWithBackgroundColor("MST JAHANARA BEGUM", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell41 = pdfFormattingUtils.getCellWithBackgroundColor("Date of Birth", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell42 = pdfFormattingUtils.getCellWithBackgroundColor("1995-08-02 [YYYY-MM-DD] 25 Years 3 Months 0 Days", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell51 = pdfFormattingUtils.getCellWithBackgroundColor("Contact No", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell52 = pdfFormattingUtils.getCellWithBackgroundColor("017******20", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell61 = pdfFormattingUtils.getCellWithBackgroundColor("Freedom Fighter Status", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell62 = pdfFormattingUtils.getCellWithBackgroundColor("Non Freedom Fighter", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell71 = pdfFormattingUtils.getCellWithBackgroundColor("Disability", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell72 = pdfFormattingUtils.getCellWithBackgroundColor("None", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell81 = pdfFormattingUtils.getCellWithBackgroundColor("National ID Number", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell detailsCell82 = pdfFormattingUtils.getCellWithBackgroundColor("5076143204", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);

            applicantsDetails.addCell(detailsCell11);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell12);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell21);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell22);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell31);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell32);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell41);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell42);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell51);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell52);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell61);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell62);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell71);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell72);
            applicantsDetails.addCell(emptyRow);

            applicantsDetails.addCell(detailsCell81);
            applicantsDetails.addCell(emptyColumn);
            applicantsDetails.addCell(detailsCell82);
            applicantsDetails.addCell(emptyRow);

            emptyRow = pdfFormattingUtils.getCellWithBackgroundColor(7, 1, 255, 255, 255);

            PdfPTable otherDetails = pdfFormattingUtils.createTable(7, 100, NARROW_SPACING, new int[]{50, 1, 99, 1, 50, 1, 98}, Element.ALIGN_LEFT);
            PdfPCell otherDetailsCell11 = pdfFormattingUtils.getCellWithBackgroundColor("Home District", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell12 = pdfFormattingUtils.getCellWithBackgroundColor("Meherpur", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell13 = pdfFormattingUtils.getCellWithBackgroundColor("Ethnic Minority", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell14 = pdfFormattingUtils.getCellWithBackgroundColor("None", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);

            PdfPCell otherDetailsCell21 = pdfFormattingUtils.getCellWithBackgroundColor("Marital Status", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell22 = pdfFormattingUtils.getCellWithBackgroundColor("Married - Spouse Name: MST KAMINI AKTER", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell23 = pdfFormattingUtils.getCellWithBackgroundColor("Employment Status", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell24 = pdfFormattingUtils.getCellWithBackgroundColor("Private Organisation", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);

            PdfPCell otherDetailsCell31 = pdfFormattingUtils.getCellWithBackgroundColor("Gender", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell32 = pdfFormattingUtils.getCellWithBackgroundColor("Male", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell33 = pdfFormattingUtils.getCellWithBackgroundColor("Height | Weight | Chest", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);
            PdfPCell otherDetailsCell34 = pdfFormattingUtils.getCellWithBackgroundColor("170 [cm] | 82 [kg] | 106 [cm]", smallFont, 0, Element.ALIGN_LEFT, 192, 192, 192);

            otherDetails.addCell(otherDetailsCell11);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell12);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell13);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell14);
            otherDetails.addCell(emptyRow);

            otherDetails.addCell(otherDetailsCell21);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell22);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell23);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell24);
            otherDetails.addCell(emptyRow);

            otherDetails.addCell(otherDetailsCell31);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell32);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell33);
            otherDetails.addCell(emptyColumn);
            otherDetails.addCell(otherDetailsCell34);


            PdfPTable addressDetails = pdfFormattingUtils.createTable(3, 100, NARROW_SPACING, new int[]{150, 1, 149}, Element.ALIGN_LEFT);
            PdfPCell addressDetailsCell11 = pdfFormattingUtils.getCellWithBackgroundColor("Present Address:", smallFont, 0, Element.ALIGN_LEFT, 160, 160, 160);
            PdfPCell addressDetailsCell12 = pdfFormattingUtils.getCellWithBackgroundColor("Permanent Address:", smallFont, 0, Element.ALIGN_LEFT, 160, 160, 160);

            emptyRow = pdfFormattingUtils.getCellWithBackgroundColor(3, 1, 255, 255, 255);

            Paragraph addressDetailsPara11 = pdfFormattingUtils.createParagraph("Care of:", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara12 = pdfFormattingUtils.createParagraph("Village/Town: Cha 37/1, North Badda", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara13 = pdfFormattingUtils.createParagraph("Post Office: Badda", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara14 = pdfFormattingUtils.createParagraph("Post Code: 1212", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara15 = pdfFormattingUtils.createParagraph("Upazilla/Thana: Badda", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara16 = pdfFormattingUtils.createParagraph("District: Dhaka", verySmallFont, 0, Element.ALIGN_LEFT);
            PdfPCell addressDetailsCell21 = pdfFormattingUtils.getCellWithBackgroundColor(Arrays.asList(addressDetailsPara11, addressDetailsPara12, addressDetailsPara13, addressDetailsPara14, addressDetailsPara15, addressDetailsPara16), 192, 192, 192);

            Paragraph addressDetailsPara21 = pdfFormattingUtils.createParagraph("Care of:", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara22 = pdfFormattingUtils.createParagraph("Village/Town: Betbaria", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara23 = pdfFormattingUtils.createParagraph("Post Office: Betbaria", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara24 = pdfFormattingUtils.createParagraph("Post Code: 7110", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara25 = pdfFormattingUtils.createParagraph("Upazilla/Thana: Gangni", verySmallFont, 0, Element.ALIGN_LEFT);
            Paragraph addressDetailsPara26 = pdfFormattingUtils.createParagraph("District: Meherpur", verySmallFont, 0, Element.ALIGN_LEFT);
            PdfPCell addressDetailsCell22 = pdfFormattingUtils.getCellWithBackgroundColor(Arrays.asList(addressDetailsPara21, addressDetailsPara22, addressDetailsPara23, addressDetailsPara24, addressDetailsPara25, addressDetailsPara26), 192, 192, 192);


            addressDetails.addCell(addressDetailsCell11);
            addressDetails.addCell(emptyColumn);
            addressDetails.addCell(addressDetailsCell12);
            addressDetails.addCell(emptyRow);

            addressDetails.addCell(addressDetailsCell21);
            addressDetails.addCell(emptyColumn);
            addressDetails.addCell(addressDetailsCell22);

            document.open();
            document.add(imageTable);
            document.add(userIdTable);
            document.add(applicantsDetails);
            document.add(otherDetails);
            document.add(addressDetails);

            document.close();

            System.out.println("Successful");

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return watermarkPdfGeneration.addPageNumberToEveryPage(inputStream);
    }


    public void generateAdmitCard(List<Student> studentList, String filename) throws IOException, DocumentException {

        Rectangle pageSize = new Rectangle(594, 423);
        pageSize.setBackgroundColor(new BaseColor(230, 230, 250));
        float margin = 50;
        Document document = new Document(pageSize, margin, margin, margin, margin);

        BaseFont scriptMTBold = BaseFont.createFont(ConstantsClass.SCRIPT_MT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont oldEnglish = BaseFont.createFont(ConstantsClass.OLD_ENGLISH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(Font.FontFamily.TIMES_ROMAN,10f, Font.ITALIC, BaseColor.BLACK);

        Font oldEnglish25 = new Font(oldEnglish, 25, Font.NORMAL, BaseColor.BLACK);
        Font oldEnglishIT18 = new Font(oldEnglish, 18, Font.ITALIC, BaseColor.BLACK);
        Font scriptMTBold11 = new Font(scriptMTBold, 11, Font.NORMAL, BaseColor.BLACK);


        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));


        document.open();

        Paragraph paragraphDummy = new Paragraph("Examinee must bring this card to the examination hall.", font);
        document.add(paragraphDummy);
        document.newPage();


        Image logoImage = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
        Image signImage = Image.getInstance(ConstantsClass.SIGNATURE_IMAGE);

        for (Student student : studentList) {

            logoImage.setAlignment(Element.ALIGN_LEFT);
            logoImage.setBorderWidth(SPACING);


            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setWidths(new int[]{1, 5});

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(logoImage);
            imageCell.setBorder(PdfPCell.NO_BORDER);
            imageTable.addCell(imageCell);


            Font font2 = new Font(oldEnglish, 12, Font.NORMAL, BaseColor.BLACK);
            PdfPCell textCell = new PdfPCell();

            Paragraph paragraph = new Paragraph("Amar Ami\n", oldEnglish25);
            paragraph.add(new Chunk("Talent Evaluation Exam - 2020\n", font2));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(paragraph);
            textCell.setBorder(Rectangle.NO_BORDER);


            imageTable.addCell(textCell);
            imageTable.setSpacingAfter(0);

            Paragraph paragraph1 = new Paragraph("Admit Card", oldEnglishIT18);
            paragraph1.setSpacingAfter(30);
            paragraph1.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{5, 3});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Name: " + student.getName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No: " + student.getRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School: " + student.getSchoolName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Registration No: " + student.getRegNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class: " + student.getClassId(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class Roll: " + student.getSchoolRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Centre: Betbaria Secondary School", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Exam Date: 09 Aug, 9.00 am", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            table.setSpacingAfter(30);


            signImage.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidths(new int[]{17, 2, 3});
            table2.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPCell signImageCell = new PdfPCell();
            signImageCell.addElement(signImage);
            signImageCell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(signImageCell);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(new int[]{8, 3});
            table3.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            cell = new PdfPCell(new Phrase("Controller of Exam", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);


            Paragraph line = new Paragraph(new Chunk(new LineSeparator(0.0F, 100.0F, BaseColor.BLACK, Element.ALIGN_LEFT, 1)));

            Paragraph paragraph2 = new Paragraph("Examinee must bring this card to the examination hall.", font);
            Paragraph paragraph3 = new Paragraph("Examinee can keep nothing with him/her in the exam hall except pen, pencil, geometry instruments & calculator.", font);

            document.add(imageTable);
            document.add(paragraph1);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(line);
            document.add(paragraph2);
            document.add(paragraph3);

            document.newPage();
        }

        document.close();
    }





}
