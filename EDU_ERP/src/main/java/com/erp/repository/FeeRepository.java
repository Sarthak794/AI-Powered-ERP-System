package com.erp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.entity.Fee;
import com.erp.entity.Student;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    Optional<Fee> findByStudent(Student student);
    
    @Query("SELECT COALESCE(SUM(f.totalAmount - f.paidAmount), 0) FROM Fee f")
    Double getTotalPending();


}
