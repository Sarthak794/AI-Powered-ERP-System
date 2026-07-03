package com.erp.service;

import com.erp.entity.Specialization;
import com.erp.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSpecializationService {

    private final SpecializationRepository specializationRepository;

    public AdminSpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    public void createSpecialization(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization name cannot be empty");
        }

        if (specializationRepository.existsByNameIgnoreCase(name.trim())) {
            throw new IllegalArgumentException("Specialization already exists");
        }

        Specialization specialization = new Specialization();
        specialization.setName(name.trim());

        specializationRepository.save(specialization);
    }

    public void deleteSpecialization(Long id) {
        specializationRepository.deleteById(id);
    }
}
