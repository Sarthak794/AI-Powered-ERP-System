package com.erp.service;

import com.erp.entity.StudentApplication;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class OfferLetterPdfService {

    public ByteArrayInputStream generateOfferLetter(StudentApplication app) {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLUE);
            Paragraph title = new Paragraph("OFFER LETTER", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new LineSeparator());
            document.add(new Paragraph(" "));

            // Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
            Paragraph date = new Paragraph("Date: " + LocalDate.now(), dateFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph(" "));

            // Greeting
            Font bodyFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            document.add(new Paragraph("Dear " + app.getStudent().getFullName() + ",", bodyFont));
            document.add(new Paragraph(" "));

            // Body Content
            document.add(new Paragraph(
                    "We are delighted to extend to you an offer to join " +
                            app.getPlacementDrive().getCompany().getCompanyName() +
                            ". Your skills and potential have impressed us, and we are confident you will make valuable contributions.",
                    bodyFont
            ));

            document.add(new Paragraph(" "));

            // Details Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            PdfPCell cell1 = new PdfPCell(new Phrase("Drive Title", headerFont));
            cell1.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell1);
            table.addCell(new Phrase(app.getPlacementDrive().getDriveTitle(), cellFont));

            PdfPCell cell2 = new PdfPCell(new Phrase("Package Offered", headerFont));
            cell2.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell2);
            table.addCell(new Phrase(app.getPlacementDrive().getPackageOffered() + " LPA", cellFont));

            PdfPCell cell3 = new PdfPCell(new Phrase("Status", headerFont));
            cell3.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell3);
            table.addCell(new Phrase("SELECTED", cellFont));

            document.add(table);

            // Closing
            document.add(new Paragraph("We look forward to your contribution and wish you great success in your career.", bodyFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Warm Regards,", bodyFont));
            document.add(new Paragraph("Training & Placement Cell", bodyFont));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating offer letter PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
