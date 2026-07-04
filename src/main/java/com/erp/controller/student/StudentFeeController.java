package com.erp.controller.student;

import com.erp.entity.Student;
import com.erp.repository.FeePaymentRepository;
import com.erp.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.erp.entity.StudentOnlinePayment;
import com.erp.repository.StudentOnlinePaymentRepository;
import com.erp.service.FeeReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/student/fees")
public class StudentFeeController {

	private final StudentRepository studentRepo;
	private final FeePaymentRepository feeRepo;
	private final StudentOnlinePaymentRepository paymentRepo;
	private final FeeReceiptService receiptService;

	public StudentFeeController(
	        StudentRepository studentRepo,
	        FeePaymentRepository feeRepo,
	        StudentOnlinePaymentRepository paymentRepo,
	        FeeReceiptService receiptService) {

	    this.studentRepo = studentRepo;
	    this.feeRepo = feeRepo;
	    this.paymentRepo = paymentRepo;
	    this.receiptService = receiptService;
	}
    @GetMapping
    public String fees(Authentication auth, Model model) {

        Student student = studentRepo
                .findByUserUsername(auth.getName())
                .orElseThrow();

        model.addAttribute("payments",
                feeRepo.findByStudent(student));

        return "student/fees";
    }
    @GetMapping("/receipt/{id}")
    public ResponseEntity<byte[]> downloadReceipt(
            @PathVariable Long id) {


        StudentOnlinePayment payment =
                paymentRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"));


        byte[] pdf =
                receiptService.generateFeeReceipt(payment);


        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fee-receipt.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}
