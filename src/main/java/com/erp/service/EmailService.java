package com.erp.service;

import java.io.ByteArrayInputStream;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReceipt(String toEmail,
                            String studentName,
                            ByteArrayInputStream receiptPdf) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Fee Payment Receipt");
            helper.setText(
                    """
                    Dear %s,

                    Thank you for your payment.
                    Please find attached your fee receipt.

                    Regards,
                    ABC Education Institute
                    """.formatted(studentName)
            );

            helper.addAttachment(
                    "Fee_Receipt.pdf",
                    () -> receiptPdf
            );

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace(); // log only, don't break flow
        }
    }
}
