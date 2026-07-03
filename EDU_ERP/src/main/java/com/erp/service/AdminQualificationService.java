package com.erp.service;

import com.erp.entity.Qualification;
import com.erp.repository.QualificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminQualificationService {

    private final QualificationRepository qualificationRepository;

    public AdminQualificationService(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    public List<Qualification> getAllQualifications() {
        return qualificationRepository.findAll();
    }

    public void createQualification(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Qualification name cannot be empty");
        }

        if (qualificationRepository.existsByNameIgnoreCase(name.trim())) {
            throw new IllegalArgumentException("Qualification already exists");
        }

        Qualification qualification = new Qualification();
        qualification.setName(name.trim());

        qualificationRepository.save(qualification);
    }

    public void deleteQualification(Long id) {
        qualificationRepository.deleteById(id);
    }
}
