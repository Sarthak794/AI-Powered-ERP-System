package com.erp.repository;

import com.erp.entity.OfferLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferLetterRepository
        extends JpaRepository<OfferLetter, Long> {

    Optional<OfferLetter> findByApplicationId(Long applicationId);

    Optional<OfferLetter> findByApplicationStudentId(Long studentId);
}