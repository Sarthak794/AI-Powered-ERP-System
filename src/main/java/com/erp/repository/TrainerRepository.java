package com.erp.repository;

import com.erp.entity.Trainer;
import com.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByEmail(String email);

    long countByUser(User user);

    Optional<Trainer> findByUser(User user);

    Trainer findByUserEmail(String email);

    Trainer findByUserUsername(String username);

}