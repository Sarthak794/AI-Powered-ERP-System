package com.erp.service;

import com.erp.entity.Trainer;
import com.erp.entity.TrainerSalaryPayment;
import com.erp.entity.User;
import com.erp.repository.TrainerRepository;
import com.erp.repository.TrainerSalaryPaymentRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerSalaryViewService {

    private final TrainerSalaryPaymentRepository paymentRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerSalaryViewService(
            TrainerSalaryPaymentRepository paymentRepository,
            TrainerRepository trainerRepository,
            UserRepository userRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    private Trainer getCurrentTrainer() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return trainerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    public List<TrainerSalaryPayment> getMySalaryHistory() {
        return paymentRepository.findByTrainer(getCurrentTrainer());
    }
}
