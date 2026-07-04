package com.erp.repository;

import com.erp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByActiveTrue();

    List<Company> findByCompanyNameContainingIgnoreCase(String companyName);
    
    long countByActiveTrue();
}