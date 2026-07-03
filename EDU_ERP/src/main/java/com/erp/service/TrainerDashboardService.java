package com.erp.service;

import com.erp.entity.Batch;
import com.erp.entity.Trainer;
import com.erp.entity.User;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.BatchRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.TrainerRepository;
import com.erp.repository.UserRepository;
import com.erp.repository.AssignmentRepository;
import com.erp.repository.AssignmentSubmissionRepository;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TrainerDashboardService {

    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
//    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;

    public TrainerDashboardService(
            BatchRepository batchRepository,
            StudentRepository studentRepository,
            AttendanceRepository attendanceRepository,
            TrainerRepository trainerRepository,
            UserRepository userRepository,
            AssignmentRepository assignmentRepository,
            AssignmentSubmissionRepository submissionRepository) {

        this.batchRepository = batchRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
		this.submissionRepository = submissionRepository;
    }
    private User getCurrentTrainer() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    public Trainer getTrainer() {

        return trainerRepository
                .findByUser(getCurrentTrainer())
                .orElseThrow();
    }

    public long getBatchCount() {
        return batchRepository.countByTrainerUser(getCurrentTrainer());
    }

    public long getStudentCount() {
        return studentRepository.countByBatchTrainerUser(getCurrentTrainer());
    }

    public Double getAverageAttendance() {

        Trainer trainer = getTrainer();

        Double avg =
                attendanceRepository.avgAttendanceByTrainer(
                        trainer.getId());

        return avg != null ? avg : 0;
    }

    public List<Batch> getMyBatches() {

        return batchRepository.findByTrainer(
                getTrainer()
        );
    }
    
    public long getAssignmentCount() {

        return assignmentRepository.countByTrainer(
                getTrainer()
        );
    }
    
    
    public long getTotalAssignments() {

        return assignmentRepository.countByTrainer(
                getTrainer()
        );
    }

    public long getTotalSubmissions() {

        return submissionRepository
                .countByAssignmentTrainerId(
                        getTrainer().getId()
                );
    }

    public long getPendingReviews() {

        return submissionRepository
                .countByAssignmentTrainerIdAndReviewedFalse(
                        getTrainer().getId()
                );
    }
}