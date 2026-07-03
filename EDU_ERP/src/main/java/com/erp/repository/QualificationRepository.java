package com.erp.repository;

import com.erp.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
	boolean existsByNameIgnoreCase(String name);
}
