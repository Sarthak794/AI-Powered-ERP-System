package com.erp.service;

import com.erp.entity.PlacementDrive;
import com.erp.repository.PlacementDriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementDriveService {

    private final PlacementDriveRepository placementDriveRepository;

    public PlacementDriveService(
            PlacementDriveRepository placementDriveRepository) {

        this.placementDriveRepository = placementDriveRepository;
    }

    public List<PlacementDrive> getAllDrives() {
        return placementDriveRepository.findAll();
    }

    public List<PlacementDrive> getActiveDrives() {
        return placementDriveRepository.findByActiveTrue();
    }

    public PlacementDrive saveDrive(PlacementDrive drive) {
        return placementDriveRepository.save(drive);
    }

    public void deleteDrive(Long id) {
        placementDriveRepository.deleteById(id);
    }
    
    public PlacementDrive getDriveById(Long id) {

        return placementDriveRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Drive not found"));
    }
    
    public long getTotalDrives() {
        return placementDriveRepository.count();
    }
}