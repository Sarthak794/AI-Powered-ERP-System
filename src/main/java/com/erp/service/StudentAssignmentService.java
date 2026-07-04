package com.erp.service;

import com.erp.entity.Assignment;
import com.erp.entity.AssignmentSubmission;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.repository.AssignmentRepository;
import com.erp.repository.AssignmentSubmissionRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AssignmentSubmissionRepository submissionRepository;

    public StudentAssignmentService(
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository,
            UserRepository userRepository,
            AssignmentSubmissionRepository submissionRepository) {

        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
    }

    private Student getCurrentStudent() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow();

        return studentRepository
                .findByUser(user)
                .orElseThrow();
    }

    public List<Assignment> getMyAssignments() {

        Student student =
                getCurrentStudent();

        return assignmentRepository
                .findByBatchId(
                        student.getBatch().getId()
                );
    }
    
    public Assignment getAssignment(Long id) {

        return assignmentRepository
                .findById(id)
                .orElseThrow();
    }
    
    public boolean alreadySubmitted(Long assignmentId) {

        Student student =
                getCurrentStudent();

        return submissionRepository
                .existsByAssignmentIdAndStudentId(
                        assignmentId,
                        student.getId()
                );
    }
    
    public void submitAssignment(
            Long assignmentId,
            String filePath
    ) {

        Student student =
                getCurrentStudent();

        if(alreadySubmitted(assignmentId)) {

            throw new RuntimeException(
                    "Assignment already submitted."
            );
        }

        AssignmentSubmission submission =
                new AssignmentSubmission();

        submission.setAssignment(
                getAssignment(assignmentId)
        );

        submission.setStudent(student);

        submission.setFilePath(filePath);

        submissionRepository.save(
                submission
        );
    }
}