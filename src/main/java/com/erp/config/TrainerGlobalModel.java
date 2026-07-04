package com.erp.config;

import com.erp.entity.Trainer;
import com.erp.entity.User;
import com.erp.repository.TrainerRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class TrainerGlobalModel {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerGlobalModel(TrainerRepository trainerRepository,
                              UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute("trainer")
    public Trainer addTrainer() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) return null;

        return trainerRepository.findByUser(user)
                .orElse(null);
    }
}