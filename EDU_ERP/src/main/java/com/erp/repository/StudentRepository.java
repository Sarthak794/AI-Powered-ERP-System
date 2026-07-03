package com.erp.repository;

import com.erp.entity.Batch;
import com.erp.entity.Student;
import com.erp.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByBatch(Batch batch);

    List<Student> findByBatchId(Long batchId);

    Optional<Student> findByUserUsername(String username);

    List<Student> findByApprovedFalse();

    boolean existsByRollNumber(String rollNumber);
    
    long countByBatchId(Long batchId);
    
    Optional<Student> findByUser(User user);


    List<Student> findByBatchIsNull();

    // ✅ CORRECT: trainer comes via batch
    List<Student> findByBatchTrainerUser(User user);

    long countByBatchTrainerUser(User user);
    
}
