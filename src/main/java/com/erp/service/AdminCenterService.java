package com.erp.service;

import com.erp.entity.Center;
import com.erp.repository.CenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCenterService {

    private final CenterRepository centerRepository;

    public AdminCenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    public void createCenter(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Center name cannot be empty");
        }

        if (centerRepository.existsByNameIgnoreCase(name.trim())) {
            throw new IllegalArgumentException("Center already exists");
        }

        Center center = new Center();
        center.setName(name.trim());

        centerRepository.save(center);
    }

    public void deleteCenter(Long id) {
        centerRepository.deleteById(id);
    }
}
