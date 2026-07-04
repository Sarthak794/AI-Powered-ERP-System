package com.erp.repository;

import com.erp.entity.Batch;
import com.erp.entity.Trainer;
import com.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    boolean existsByName(String name);

    List<Batch> findByTrainer(Trainer trainer);

    long countByTrainer(Trainer trainer);

    long countByTrainerId(Long trainerId);

    // Optional if needed elsewhere
    List<Batch> findByTrainerUser(User user);

    List<Batch> findByTrainerUserId(Long userId);

    long countByTrainerUser(User user);

    long countByTrainerUserId(Long userId);
}