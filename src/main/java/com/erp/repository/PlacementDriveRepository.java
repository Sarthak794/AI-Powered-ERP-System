package com.erp.repository;

import com.erp.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlacementDriveRepository
        extends JpaRepository<PlacementDrive, Long> {

    List<PlacementDrive> findByActiveTrue();
    
    long countByActiveTrue();

    @Query("""
    SELECT COALESCE(MAX(p.packageOffered),0)
    FROM PlacementDrive p
    """)
    Double getHighestPackage();

    @Query("""
    SELECT COALESCE(AVG(p.packageOffered),0)
    FROM PlacementDrive p
    """)
    Double getAveragePackage();
}