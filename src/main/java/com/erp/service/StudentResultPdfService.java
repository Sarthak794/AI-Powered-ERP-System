package com.erp.service;

import com.erp.entity.ExamAttempt;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class StudentResultPdfService {

    public ByteArrayInputStream generateScorecard(ExamAttempt attempt) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Exam Scorecard", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Student Name: ",
                    labelFont));
            document.add(new Paragraph(
                    attempt.getStudent().getFullName(), valueFont));

            document.add(new Paragraph("Exam Title: ",
                    labelFont));
            document.add(new Paragraph(
                    attempt.getExam().getTitle(), valueFont));

            document.add(new Paragraph("Score: ",
                    labelFont));
            document.add(new Paragraph(
                    attempt.getScore() + " / " +
                    attempt.getExam().getTotalMarks(), valueFont));

            double percentage =
                    (attempt.getScore() * 100.0) /
                    attempt.getExam().getTotalMarks();

            document.add(new Paragraph("Percentage: ",
                    labelFont));
            document.add(new Paragraph(
                    String.format("%.2f %%", percentage), valueFont));

            document.add(new Paragraph("Submitted At: ",
                    labelFont));
            document.add(new Paragraph(
                    attempt.getSubmittedAt().toString(), valueFont));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
