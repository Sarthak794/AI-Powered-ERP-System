package com.erp.repository;

import com.erp.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, Long> {
	boolean existsByNameIgnoreCase(String name);
}
