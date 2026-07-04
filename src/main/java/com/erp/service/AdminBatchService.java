package com.erp.service;

import com.erp.entity.*;
import com.erp.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBatchService {

    private final BatchRepository batchRepository;
    private final CenterRepository centerRepository;
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;
    private final TrainerRepository trainerRepository;
    private final StudentRepository studentRepository;

    public AdminBatchService(
            BatchRepository batchRepository,
            CenterRepository centerRepository,
            QualificationRepository qualificationRepository,
            SpecializationRepository specializationRepository,
            TrainerRepository trainerRepository,
            StudentRepository studentRepository
    ) {
        this.batchRepository = batchRepository;
        this.centerRepository = centerRepository;
        this.qualificationRepository = qualificationRepository;
        this.specializationRepository = specializationRepository;
        this.trainerRepository = trainerRepository;
        this.studentRepository = studentRepository;
    }

    public void createBatch(
            String name,
            Long centerId,
            Long qualificationId,
            Long specializationId,
            Long trainerId
    ) {
        if (batchRepository.existsByName(name)) {
            throw new IllegalArgumentException("Batch name already exists");
        }

        Batch batch = new Batch();
        batch.setName(name);
        batch.setCenter(centerRepository.findById(centerId).orElseThrow());
        batch.setQualification(qualificationRepository.findById(qualificationId).orElseThrow());
        batch.setSpecialization(specializationRepository.findById(specializationId).orElseThrow());
        batch.setTrainer(trainerRepository.findById(trainerId).orElseThrow());

        batchRepository.save(batch);
    }

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    public List<Student> getUnassignedStudents() {
        return studentRepository.findByBatchIsNull();
    }

    public void assignStudentToBatch(Long studentId, Long batchId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Batch batch = batchRepository.findById(batchId).orElseThrow();

        student.setBatch(batch);
        studentRepository.save(student);
    }
}
