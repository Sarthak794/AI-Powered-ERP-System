package com.erp.service.impl;

import org.springframework.stereotype.Service;

import com.erp.entity.Trainer;
import com.erp.repository.TrainerRepository;
import com.erp.service.TrainerProfileService;

@Service
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerRepository trainerRepository;

    public TrainerProfileServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Trainer getLoggedInTrainer(String username) {

        Trainer trainer = trainerRepository.findByUserUsername(username);

        if (trainer == null) {
            throw new RuntimeException(
                    "Trainer profile not found for username : " + username);
        }

        return trainer;
    }
}