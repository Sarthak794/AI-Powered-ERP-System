package com.erp.repository;

import com.erp.entity.Trainer;
import com.erp.entity.TrainerSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerSalaryRepository
        extends JpaRepository<TrainerSalary, Long> {

    Optional<TrainerSalary> findByTrainer(Trainer trainer);
}
