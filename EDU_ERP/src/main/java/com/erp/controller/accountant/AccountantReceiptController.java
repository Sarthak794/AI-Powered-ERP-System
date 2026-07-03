package com.erp.controller.accountant;

import com.erp.entity.Payment;
import com.erp.repository.PaymentRepository;
import com.erp.service.AccountantReceiptPdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;

@Controller
public class AccountantReceiptController {

    private final PaymentRepository paymentRepository;
    private final AccountantReceiptPdfService pdfService;

    public AccountantReceiptController(
            PaymentRepository paymentRepository,
            AccountantReceiptPdfService pdfService
    ) {
        this.paymentRepository = paymentRepository;
        this.pdfService = pdfService;
    }

    @GetMapping("/accountant/payments/{id}/receipt")
    public ResponseEntity<InputStreamResource> downloadReceipt(
            @PathVariable Long id
    ) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        ByteArrayInputStream pdf =
                pdfService.generateReceipt(payment);

        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Content-Disposition",
                "attachment; filename=fee-receipt-" + id + ".pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
