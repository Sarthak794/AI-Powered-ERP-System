package com.erp.service;

import com.erp.entity.*;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.StudentApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentApplicationService {

    private final StudentApplicationRepository repository;
    private final AttendanceRepository attendanceRepository;
    private final PlacementEmailService emailService;

    public StudentApplicationService(
            StudentApplicationRepository repository,
            AttendanceRepository attendanceRepository,
            PlacementEmailService emailService) {

        this.repository = repository;
        this.attendanceRepository = attendanceRepository;
        this.emailService = emailService;
    }
    
    public boolean hasApplied(Student student, PlacementDrive drive) {

        if (student == null || drive == null) {
            return false;
        }

        return repository.existsByStudentIdAndPlacementDriveId(
                student.getId(),
                drive.getId()
        );
    }
    public boolean isEligible(Student student, PlacementDrive drive) {

        if (student == null || drive == null) {
            return false;
        }

        // Qualification Check
        if (drive.getQualification() != null) {

            if (student.getQualification() == null) {
                return false;
            }

            if (!drive.getQualification().getId()
                    .equals(student.getQualification().getId())) {
                return false;
            }
        }

        // Specialization Check
        if (drive.getSpecialization() != null) {

            if (student.getSpecialization() == null) {
                return false;
            }

            if (!drive.getSpecialization().getId()
                    .equals(student.getSpecialization().getId())) {
                return false;
            }
        }

        // Attendance Check
        Double attendance =
                attendanceRepository.getAttendancePercent(student.getId());

        if (attendance == null) {
            attendance = 0.0;
        }

        return attendance >= drive.getMinimumAttendance();
    }

    /* =========================
        APPLY FLOW
    ========================= */

    public void apply(Student student, PlacementDrive drive) {

        if (repository.existsByStudentIdAndPlacementDriveId(
                student.getId(), drive.getId())) {
            throw new RuntimeException("Already applied for this drive");
        }

        StudentApplication app = new StudentApplication();
        app.setStudent(student);
        app.setPlacementDrive(drive);
        app.setAppliedAt(LocalDateTime.now());
        app.setStatus(ApplicationStatus.APPLIED);

        repository.save(app);

        emailService.sendPlacementStatusEmail(
                student.getEmail(),
                student.getFullName(),
                drive.getCompany().getCompanyName(),
                "APPLICATION SUBMITTED"
        );
    }

    /* =========================
        STATUS UPDATE
    ========================= */

    public void updateStatus(Long id, String status, String remarks) {

        StudentApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status));
        app.setRemarks(remarks);

        repository.save(app);

        emailService.sendPlacementStatusEmail(
                app.getStudent().getEmail(),
                app.getStudent().getFullName(),
                app.getPlacementDrive().getCompany().getCompanyName(),
                status
        );
    }

    /* =========================
        BASIC FETCH
    ========================= */

    public List<StudentApplication> getAllApplications() {
        return repository.findAll();
    }

    public List<StudentApplication> getApplicationsByStudent(Student student) {
        return repository.findByStudent(student);
    }

    public StudentApplication getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    /* =========================
        SELECTED LIST (FIXED)
    ========================= */

    public List<StudentApplication> getSelectedStudents() {
        return repository.findByStatus(ApplicationStatus.SELECTED);
    }

    public List<StudentApplication> getSelectedApplications() {
        return repository.findByStatus(ApplicationStatus.SELECTED);
    }

    /* =========================
        KPI METHODS
    ========================= */

    public long getTotalApplications() {
        return repository.countTotalApplications();
    }

    public long getSelectedCount() {
        return repository.countSelected();
    }

    public long getRejectedCount() {
        return repository.countRejected();
    }

    public long getShortlistedCount() {
        return repository.countShortlisted();
    }

    public long getResumeUploadedCount() {
        return repository.countResumeUploaded();
    }

    public double getPlacementRate() {

        long total = getTotalApplications();
        if (total == 0) return 0;

        return Math.round(
                (getSelectedCount() * 100.0 / total) * 100.0
        ) / 100.0;
    }

    /* =========================
        CHART DATA (RAW)
    ========================= */

    public List<Object[]> getMonthlyPlacements() {
        return repository.getMonthlyPlacements();
    }

    public List<Object[]> getCompanyWisePlacements() {
        return repository.getCompanyWisePlacements();
    }

    public List<Object[]> getBatchWisePlacements() {
        return repository.getBatchWisePlacements();
    }
}