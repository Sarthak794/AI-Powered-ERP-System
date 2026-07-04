package com.erp.service;

import com.erp.entity.Assignment;
import com.erp.entity.AssignmentSubmission;
import com.erp.entity.Batch;
import com.erp.entity.Trainer;
import com.erp.entity.User;
import com.erp.repository.AssignmentRepository;
import com.erp.repository.AssignmentSubmissionRepository;
import com.erp.repository.BatchRepository;
import com.erp.repository.TrainerRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerAssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final BatchRepository batchRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    
    public AssignmentSubmission getSubmission(
            Long id
    ) {

        return submissionRepository
                .findById(id)
                .orElseThrow();
    }
    
    

    public TrainerAssignmentService(
            AssignmentRepository assignmentRepository,
            BatchRepository batchRepository,
            TrainerRepository trainerRepository,
            UserRepository userRepository,
            AssignmentSubmissionRepository
            submissionRepository) {

        this.assignmentRepository = assignmentRepository;
        this.batchRepository = batchRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
		this.submissionRepository = submissionRepository;
    }

    private Trainer getTrainer() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow();

        return trainerRepository
                .findByUser(user)
                .orElseThrow();
    }

    public List<Assignment> getMyAssignments() {

        return assignmentRepository.findByTrainer(
                getTrainer()
        );
    }

    public List<Batch> getMyBatches() {

        return batchRepository.findByTrainer(
                getTrainer()
        );
    }
    
    public void createAssignment(
            Assignment assignment) {

        assignment.setTrainer(
                getTrainer()
        );

        assignmentRepository.save(
                assignment
        );
    }

    public Assignment getAssignment(
            Long id) {

        return assignmentRepository
                .findById(id)
                .orElseThrow();
    }

    public void deleteAssignment(
            Long id) {

        assignmentRepository.deleteById(id);
    }
    public List<AssignmentSubmission>
    getSubmissions(Long assignmentId) {

        return submissionRepository
                .findByAssignmentId(assignmentId);
    }
    
    public long getTotalSubmissions() {

        Trainer trainer = getTrainer();

        return submissionRepository
                .countByAssignmentTrainerId(
                        trainer.getId()
                );
    }

    public long getPendingReviews() {

        Trainer trainer = getTrainer();

        return submissionRepository
                .countByAssignmentTrainerIdAndReviewedFalse(
                        trainer.getId()
                );
    }
    
    public void reviewSubmission(
            Long submissionId,
            Integer marks,
            String remarks
    ) {

        AssignmentSubmission submission =
                getSubmission(submissionId);

        submission.setReviewed(true);

        submission.setMarksAwarded(marks);

        submission.setTrainerRemarks(remarks);

        submissionRepository.save(
                submission
        );
    }
    
}