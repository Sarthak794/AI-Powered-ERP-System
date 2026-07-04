package com.erp.repository;

import com.erp.entity.ExamProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ExamProgressRepository
        extends JpaRepository<ExamProgress, Long> {

    Optional<ExamProgress> findByExamIdAndStudentIdAndQuestionId(
            Long examId,
            Long studentId,
            Long questionId
    );

    List<ExamProgress> findByExamIdAndStudentId(
            Long examId,
            Long studentId
    );
    @Modifying
    @Transactional
    void deleteByExamIdAndStudentId(
            Long examId,
            Long studentId
    );
    
}