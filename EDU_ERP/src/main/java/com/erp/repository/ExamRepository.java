package com.erp.repository;

import com.erp.entity.Batch;
import com.erp.entity.Exam;
import com.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByTrainer(User trainer);

    List<Exam> findByBatchAndActiveTrue(Batch batch);

    long countByBatch(Batch batch);
    
    Optional<Exam> findById(Long id);
    
    List<Exam> findByBatchAndActiveTrueAndStartDateAfter(
            Batch batch,
            LocalDate date
    );
}