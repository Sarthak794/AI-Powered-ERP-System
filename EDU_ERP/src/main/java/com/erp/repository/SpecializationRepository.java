package com.erp.repository;

import com.erp.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
	boolean existsByNameIgnoreCase(String name);

}
