package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.entity.Assignment;
import com.erp.entity.Trainer;

@Repository
public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTrainer(Trainer trainer);

    List<Assignment> findByBatchId(Long batchId);

    long countByTrainer(Trainer trainer);
    
    
}