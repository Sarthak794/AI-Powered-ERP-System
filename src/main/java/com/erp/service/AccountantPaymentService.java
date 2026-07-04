package com.erp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.erp.entity.Fee;
import com.erp.entity.Payment;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.repository.FeeRepository;
import com.erp.repository.PaymentRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;

@Service
public class AccountantPaymentService {

    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public AccountantPaymentService(
            FeeRepository feeRepository,
            PaymentRepository paymentRepository,
            StudentRepository studentRepository,
            UserRepository userRepository
    ) {
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentAccountant() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    // Return all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Return all fees (for fee dashboard)
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    public void collectPayment(Long studentId, int amount) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Fee fee = feeRepository.findByStudent(student)
                .orElseThrow(() -> new RuntimeException("Fee record not found"));

        if (amount <= 0 || amount > fee.getPendingAmount()) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        // Update fee
        fee.setPaidAmount(fee.getPaidAmount() + amount);
        feeRepository.save(fee);

        // Save payment
        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setAmount(amount);
        payment.setPaidAt(LocalDateTime.now());
        payment.setAccountant(getCurrentAccountant());

        paymentRepository.save(payment);
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Fee fee = feeRepository.findByStudent(payment.getStudent())
                .orElseThrow(() -> new RuntimeException("Fee record not found"));

        // Revert fee
        fee.setPaidAmount(fee.getPaidAmount() - payment.getAmount());
        feeRepository.save(fee);

        // Delete payment record
        paymentRepository.delete(payment);
    }
}
