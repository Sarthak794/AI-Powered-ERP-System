package com.erp.service;

import com.erp.entity.*;
import com.erp.enums.Role;
import com.erp.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final CenterRepository centerRepository;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminTrainerService(
            TrainerRepository trainerRepository,
            UserRepository userRepository,
            CenterRepository centerRepository,
            SpecializationRepository specializationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
        this.specializationRepository = specializationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createTrainer(
            String fullName,
            String email,
            String phone,
            String username,
            String password,
            Long centerId,
            Long specializationId
    ) {
        if (trainerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Trainer email already exists");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.TRAINER);
        user.setEnabled(true);
        userRepository.save(user);

        Trainer trainer = new Trainer();
        trainer.setFullName(fullName);
        trainer.setEmail(email);
        trainer.setPhone(phone);
        trainer.setUser(user);
        trainer.setCenter(centerRepository.findById(centerId).orElseThrow());
        trainer.setSpecialization(
                specializationRepository.findById(specializationId).orElseThrow()
        );

        trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow();
        userRepository.delete(trainer.getUser());
        trainerRepository.delete(trainer);
    }
}
