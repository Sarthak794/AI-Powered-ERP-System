package com.erp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PlacementEmailService {

    private final JavaMailSender mailSender;

    public PlacementEmailService(
            JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    public void sendPlacementStatusEmail(
            String to,
            String studentName,
            String companyName,
            String status) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(
                "Placement Application Update");

        String body = """
                Dear %s,

                Your application status has been updated.

                Company : %s
                Status  : %s

                Please login to ERP portal for details.

                Best Regards,
                Training & Placement Cell
                """
                .formatted(
                        studentName,
                        companyName,
                        status);

        message.setText(body);

        mailSender.send(message);
    }
}