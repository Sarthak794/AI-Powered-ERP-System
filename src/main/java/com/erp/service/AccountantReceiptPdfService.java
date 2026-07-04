package com.erp.service;

import com.erp.entity.Payment;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class AccountantReceiptPdfService {

    public ByteArrayInputStream generateReceipt(Payment payment) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("FEE PAYMENT RECEIPT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Receipt ID: ", labelFont));
            document.add(new Paragraph(payment.getId().toString(), valueFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Student Name: ", labelFont));
            document.add(new Paragraph(payment.getStudent().getFullName(), valueFont));

            document.add(new Paragraph("Batch: ", labelFont));
            document.add(new Paragraph(payment.getStudent().getBatch().getName(), valueFont));

            document.add(new Paragraph("Amount Paid: ", labelFont));
            document.add(new Paragraph("₹ " + payment.getAmount(), valueFont));

            document.add(new Paragraph("Payment Date: ", labelFont));
            document.add(new Paragraph(payment.getPaidAt().toString(), valueFont));

            document.add(new Paragraph("Collected By: ", labelFont));
            document.add(new Paragraph(payment.getAccountant().getUsername(), valueFont));

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Status: PAID", labelFont));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating receipt PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
