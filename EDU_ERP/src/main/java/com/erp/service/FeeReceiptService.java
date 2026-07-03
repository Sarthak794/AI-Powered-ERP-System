package com.erp.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.erp.entity.StudentOnlinePayment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

@Service
public class FeeReceiptService {

    public byte[] generateFeeReceipt(StudentOnlinePayment payment) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // ===== LOGO =====
            try {
                ImageData imageData = ImageDataFactory.create(
                        getClass().getResource("/static/images/logo.png")
                );
                Image logo = new Image(imageData)
                        .scaleToFit(80, 80)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER);
                doc.add(logo);
            } catch (Exception ignored) {}

            // ===== INSTITUTE NAME =====
            doc.add(new Paragraph("SR EDUCATION INSTITUTE")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(16));

            doc.add(new Paragraph("Main Road, Pune - 411001\nPhone: 7709655702")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY));

            doc.add(new LineSeparator(new SolidLine()));
            doc.add(new Paragraph("\n"));

            // ===== TITLE =====
            doc.add(new Paragraph("FEE PAYMENT RECEIPT")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(14));

            doc.add(new Paragraph("\n"));

            // ===== TABLE =====
            Table table = new Table(2).useAllAvailableWidth();

            table.addCell(cell("Receipt No"));
            table.addCell(cell("REC-" + payment.getId()));

            table.addCell(cell("Student Name"));
            table.addCell(cell(payment.getStudent().getUser().getUsername()));

            table.addCell(cell("Amount Paid"));
            table.addCell(cell("₹ " + payment.getAmount()));

            table.addCell(cell("Payment Mode"));
            table.addCell(cell("ONLINE / RAZORPAY"));

            table.addCell(cell("Transaction ID"));
            table.addCell(cell(payment.getRazorpayPaymentId()));

            table.addCell(cell("Payment Status"));
            table.addCell(cell(payment.getStatus()));

            table.addCell(cell("Payment Date"));
            table.addCell(cell(
                    payment.getPaymentTime()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
            ));

            doc.add(table);

            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("This is a system-generated receipt. No signature required.")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(9)
                    .setFontColor(ColorConstants.GRAY));

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate fee receipt PDF", e);
        }
    }

    private Cell cell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setPadding(6);
    }
}
