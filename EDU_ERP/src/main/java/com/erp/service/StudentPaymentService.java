package com.erp.service;

import com.erp.entity.*;
import com.erp.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentPaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;
    private final StudentOnlinePaymentRepository paymentRepository;

    public StudentPaymentService(
            StudentRepository studentRepository,
            FeeRepository feeRepository,
            StudentOnlinePaymentRepository paymentRepository
    ) {
        this.studentRepository = studentRepository;
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
    }

    public Order createOrder(String username, int amount) throws Exception {

        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow();

        RazorpayClient client =
                new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // paise
        options.put("currency", "INR");
        options.put("receipt", "fee_" + student.getId());

        return client.orders.create(options);
    }

    public void verifyAndSavePayment(
            String username,
            int amount,
            String orderId,
            String paymentId
    ) {
        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow();

        Fee fee = feeRepository.findByStudent(student)
                .orElseThrow();

        // Update fee
        fee.setPaidAmount(fee.getPaidAmount() + amount);
        feeRepository.save(fee);

        // Save payment record
        StudentOnlinePayment payment = new StudentOnlinePayment();
        payment.setStudent(student);
        payment.setAmount(amount);
        payment.setRazorpayOrderId(orderId);
        payment.setRazorpayPaymentId(paymentId);
        payment.setStatus("SUCCESS");
        payment.setPaymentTime(LocalDateTime.now());

        paymentRepository.save(payment);
    }
}
