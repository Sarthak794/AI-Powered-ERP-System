package com.erp.repository;

import com.erp.entity.ExamAttempt;
import com.erp.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    // Student attempts
    List<ExamAttempt> findByStudentId(Long studentId);

    // Check if student already attempted exam
    boolean existsByExamIdAndStudentId(
            Long examId,
            Long studentId
    );

    // Get specific attempt
    ExamAttempt findByExamIdAndStudentId(
            Long examId,
            Long studentId
    );

    @Query("""
        SELECT AVG(e.score)
        FROM ExamAttempt e
        WHERE e.student.batch.id = :batchId
    """)
    Double avgMarksByBatch(@Param("batchId") Long batchId);

    @Query("""
        SELECT MAX(e.score)
        FROM ExamAttempt e
        WHERE e.student.batch.id = :batchId
    """)
    Integer maxMarksByBatch(@Param("batchId") Long batchId);

    @Query("""
        SELECT e.student.fullName
        FROM ExamAttempt e
        WHERE e.student.batch.id = :batchId
        ORDER BY e.score DESC
    """)
    List<String> topStudent(@Param("batchId") Long batchId);
    List<ExamAttempt> findByStudent(Student student);
    List<ExamAttempt> findByExamId(Long examId);
    long countByExamId(Long examId);
}