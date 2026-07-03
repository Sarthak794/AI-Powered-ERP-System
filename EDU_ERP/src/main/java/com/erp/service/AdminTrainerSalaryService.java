package com.erp.service;

import com.erp.entity.Trainer;
import com.erp.entity.TrainerSalary;
import com.erp.repository.TrainerRepository;
import com.erp.repository.TrainerSalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTrainerSalaryService {

    private final TrainerRepository trainerRepository;
    private final TrainerSalaryRepository salaryRepository;

    public AdminTrainerSalaryService(
            TrainerRepository trainerRepository,
            TrainerSalaryRepository salaryRepository
    ) {
        this.trainerRepository = trainerRepository;
        this.salaryRepository = salaryRepository;
    }

    public List<TrainerSalary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public void allotSalary(Long trainerId, int monthlyAmount) {

        if (monthlyAmount <= 0) {
            throw new IllegalArgumentException("Invalid salary amount");
        }

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        TrainerSalary salary = salaryRepository
                .findByTrainer(trainer)
                .orElseGet(() -> {
                    TrainerSalary s = new TrainerSalary();
                    s.setTrainer(trainer);
                    return s;
                });

        salary.setMonthlyAmount(monthlyAmount);
        salaryRepository.save(salary);
    }
}
