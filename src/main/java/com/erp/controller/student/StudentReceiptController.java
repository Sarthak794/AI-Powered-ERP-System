package com.erp.controller.student;

import com.erp.entity.StudentOnlinePayment;
import com.erp.repository.StudentOnlinePaymentRepository;
import com.erp.service.FeeReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/receipt")
public class StudentReceiptController {

    private final StudentOnlinePaymentRepository paymentRepository;
    private final FeeReceiptService receiptService;

    public StudentReceiptController(
            StudentOnlinePaymentRepository paymentRepository,
            FeeReceiptService receiptService
    ) {
        this.paymentRepository = paymentRepository;
        this.receiptService = receiptService;
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable Long paymentId) {

        StudentOnlinePayment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        byte[] pdf = receiptService.generateFeeReceipt(payment);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fee-receipt-" + paymentId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
