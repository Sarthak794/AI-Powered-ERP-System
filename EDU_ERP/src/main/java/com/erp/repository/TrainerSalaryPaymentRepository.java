package com.erp.repository;

import com.erp.entity.Trainer;
import com.erp.entity.TrainerSalaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerSalaryPaymentRepository
        extends JpaRepository<TrainerSalaryPayment, Long> {

    List<TrainerSalaryPayment> findByTrainer(Trainer trainer);

    boolean existsByTrainerAndMonth(Trainer trainer, String month);
}
