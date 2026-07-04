package com.erp.service;

import com.erp.entity.ExamAttempt;
import com.erp.entity.Fee;
import com.erp.entity.Student;
import com.erp.repository.ExamAttemptRepository;
import com.erp.repository.FeeRepository;
import com.erp.repository.StudentDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDashboardService {

    private final ExamAttemptRepository examAttemptRepository;
    private final FeeRepository feeRepository;
    private final StudentDocumentRepository documentRepository;

    public StudentDashboardService(
            ExamAttemptRepository examAttemptRepository,
            FeeRepository feeRepository,
            StudentDocumentRepository documentRepository
    ) {
        this.examAttemptRepository = examAttemptRepository;
        this.feeRepository = feeRepository;
        this.documentRepository = documentRepository;
    }

    public double getAverageScore(Student student) {

        List<ExamAttempt> attempts =
                examAttemptRepository.findByStudent(student);

        if(attempts.isEmpty()) {
            return 0;
        }

        return attempts.stream()
                .mapToInt(ExamAttempt::getScore)
                .average()
                .orElse(0);
    }

    public int getHighestScore(Student student) {

        return examAttemptRepository
                .findByStudent(student)
                .stream()
                .mapToInt(ExamAttempt::getScore)
                .max()
                .orElse(0);
    }

    public long getDocumentCount(Student student) {

        return documentRepository
                .findByStudentIdAndIsActiveTrue(student.getId())
                .size();
    }

    public double getPendingFees(Student student) {

        return feeRepository
                .findByStudent(student)
                .map(Fee::getPendingAmount)
                .orElse(0);
    }
    
    public int getProfileCompletionPercentage(Student student) {

        int totalFields = 10;
        int completed = 0;

        if (student.getPhone() != null && !student.getPhone().isBlank())
            completed++;

        if (student.getEmail() != null && !student.getEmail().isBlank())
            completed++;

        if (student.getAddress() != null && !student.getAddress().isBlank())
            completed++;

        if (student.getDob() != null)
            completed++;

        if (student.getGender() != null && !student.getGender().isBlank())
            completed++;

        if (student.getProfilePhoto() != null && !student.getProfilePhoto().isBlank())
            completed++;

        if (student.getResumePath() != null && !student.getResumePath().isBlank())
            completed++;

        if (student.getBatch() != null)
            completed++;

        if (student.getQualification() != null)
            completed++;

        if (student.getSpecialization() != null)
            completed++;

        return (completed * 100) / totalFields;
    }
    

}