package com.erp.service;

import com.erp.dto.*;
import com.erp.entity.*;
import com.erp.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerBatchService {

    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerBatchService(
            BatchRepository batchRepository,
            StudentRepository studentRepository,
            AttendanceRepository attendanceRepository,
            ExamAttemptRepository examAttemptRepository,
            TrainerRepository trainerRepository,
            UserRepository userRepository
    ) {
        this.batchRepository = batchRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username =
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    private Trainer getTrainer() {
        return trainerRepository.findByUser(getCurrentUser())
                .orElseThrow();
    }

    // =========================
    // STEP 5: STUDENTS
    // =========================
    public List<BatchStudentDto> getBatchStudents(Long batchId) {

        List<Student> students =
                studentRepository.findByBatchId(batchId);

        List<BatchStudentDto> list = new ArrayList<>();

        for (Student s : students) {

            BatchStudentDto dto = new BatchStudentDto();

            dto.setRollNumber(s.getRollNumber());
            dto.setName(s.getFullName());
            dto.setEmail(s.getEmail());
            dto.setPhone(s.getPhone());

            // attendance %
            Double att = attendanceRepository.getAttendancePercent(s.getId());
            dto.setAttendancePercentage(att != null ? att : 0);

            // exam score
            List<ExamAttempt> attempts =
                    examAttemptRepository.findByStudentId(s.getId());

            int avgScore = attempts.stream()
                    .mapToInt(ExamAttempt::getScore)
                    .sum();

            dto.setExamScore(avgScore);

            list.add(dto);
        }

        return list;
    }

    // =========================
    // STEP 6: PERFORMANCE
    // =========================
    public BatchPerformanceDto getBatchPerformance(Long batchId) {

        List<Student> students =
                studentRepository.findByBatchId(batchId);

        BatchPerformanceDto dto = new BatchPerformanceDto();

        dto.setTotalStudents(students.size());

        Double avgAtt = attendanceRepository.avgAttendanceByBatch(batchId);
        dto.setAvgAttendance(avgAtt != null ? avgAtt : 0);

        Double avgExam = examAttemptRepository.avgMarksByBatch(batchId);
        dto.setAvgExamScore(avgExam != null ? avgExam : 0);

        List<String> top = examAttemptRepository.topStudent(batchId);
        dto.setTopPerformer(top.isEmpty() ? "N/A" : top.get(0));

        dto.setLowAttendanceCount(
                attendanceRepository.countLowAttendance(batchId, 75)
        );

        return dto;
    }

    // =========================
    // STEP 7: KPI
    // =========================
    public TrainerKpiDto getKpis() {

        Trainer trainer = getTrainer();

        TrainerKpiDto dto = new TrainerKpiDto();

        dto.setTotalBatches(batchRepository.countByTrainer(trainer));
        dto.setTotalStudents(studentRepository.countByBatchTrainerUser(trainer.getUser()));
        dto.setTotalExams(0); // optional if exam repo added

        dto.setAvgAttendance(attendanceRepository.avgAttendanceByTrainer(trainer.getId()));

        return dto;
    }
    
    public List<TrainerBatchDashboardDto> getTrainerBatchDashboard() {

        Trainer trainer = getTrainer();

        List<Batch> batches = batchRepository.findByTrainer(trainer);

        System.out.println("Trainer ID = " + trainer.getId());
        System.out.println("Batches found = " + batches.size());

        List<TrainerBatchDashboardDto> result = new ArrayList<>();

        for (Batch batch : batches) {

            TrainerBatchDashboardDto dto =
                    new TrainerBatchDashboardDto();

            dto.setBatchId(batch.getId());
            dto.setBatchName(batch.getName());

            if (batch.getCenter() != null) {
                dto.setCenterName(batch.getCenter().getName());
            }

            if (batch.getQualification() != null) {
                dto.setQualificationName(
                        batch.getQualification().getName()
                );
            }

            if (batch.getSpecialization() != null) {
                dto.setSpecializationName(
                        batch.getSpecialization().getName()
                );
            }

            long studentCount =
                    studentRepository.findByBatchId(batch.getId()).size();

            dto.setStudentCount((int) studentCount);

            dto.setExamCount(0); // replace later if exam repository exists

            result.add(dto);
        }

        return result;
    }
}