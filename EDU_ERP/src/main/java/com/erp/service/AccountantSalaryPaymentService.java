package com.erp.service;

import com.erp.entity.*;
import com.erp.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AccountantSalaryPaymentService {

    private final TrainerSalaryRepository salaryRepository;
    private final TrainerSalaryPaymentRepository paymentRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public AccountantSalaryPaymentService(
            TrainerSalaryRepository salaryRepository,
            TrainerSalaryPaymentRepository paymentRepository,
            TrainerRepository trainerRepository,
            UserRepository userRepository
    ) {
        this.salaryRepository = salaryRepository;
        this.paymentRepository = paymentRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentAccountant() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<TrainerSalary> getAllTrainerSalaries() {
        return salaryRepository.findAll();
    }

    public List<TrainerSalaryPayment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void paySalary(Long trainerId, int amount, String month) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        if (paymentRepository.existsByTrainerAndMonth(trainer, month)) {
            throw new IllegalArgumentException(
                    "Salary already paid for " + month
            );
        }

        TrainerSalary salary = salaryRepository.findByTrainer(trainer)
                .orElseThrow(() ->
                        new RuntimeException("Salary not allotted"));

        if (amount <= 0 || amount > salary.getMonthlyAmount()) {
            throw new IllegalArgumentException("Invalid salary amount");
        }

        TrainerSalaryPayment payment = new TrainerSalaryPayment();
        payment.setTrainer(trainer);
        payment.setAmount(amount);
        payment.setMonth(month);
        payment.setPaymentDate(LocalDate.now());
        payment.setAccountant(getCurrentAccountant());

        paymentRepository.save(payment);
    }
}
