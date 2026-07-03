package com.erp.service;

import com.erp.entity.Trainer;

public interface TrainerProfileService {

    Trainer getLoggedInTrainer(String username);

}