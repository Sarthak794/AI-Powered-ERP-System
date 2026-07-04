package com.erp.repository;

import com.erp.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long> {

    List<AssignmentSubmission>
    findByAssignmentId(Long assignmentId);

    List<AssignmentSubmission>
    findByStudentId(Long studentId);

    long countByAssignmentId(Long assignmentId);

    boolean existsByAssignmentIdAndStudentId(
            Long assignmentId,
            Long studentId
    );
    
    long countByStudentId(Long studentId);

    long countByReviewedFalse();

    long countByAssignmentTrainerId(Long trainerId);

    long countByAssignmentTrainerIdAndReviewedFalse(Long trainerId);
    
    Optional<AssignmentSubmission>
    findByAssignmentIdAndStudentId(
            Long assignmentId,
            Long studentId
    );
    
    
}