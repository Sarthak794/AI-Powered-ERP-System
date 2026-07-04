package com.erp.service;

import com.erp.entity.OfferLetter;
import com.erp.entity.StudentApplication;
import com.erp.repository.OfferLetterRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferLetterService {

    private final OfferLetterRepository repository;

    public OfferLetterService(
            OfferLetterRepository repository) {

        this.repository = repository;
    }

    public OfferLetter save(
            StudentApplication application,
            String fileName) {

        OfferLetter letter =
                repository
                        .findByApplicationId(
                                application.getId())
                        .orElse(new OfferLetter());

        letter.setApplication(application);
        letter.setFileName(fileName);
        letter.setUploadedAt(LocalDateTime.now());

        return repository.save(letter);
    }

    public OfferLetter getByApplicationId(Long applicationId) {

        return repository
                .findByApplicationId(applicationId)
                .orElse(null);
    }
}