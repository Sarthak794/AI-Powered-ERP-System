package com.erp.repository;

import com.erp.entity.ExamAnswer;
import com.erp.entity.ExamAttempt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAnswerRepository
extends JpaRepository<ExamAnswer, Long> {

List<ExamAnswer> findByAttemptId(Long attemptId);

List<ExamAnswer> findByAttempt(ExamAttempt attempt);

long countByQuestionId(Long questionId);

long countByQuestionIdAndSelectedOptionCorrectTrue(
        Long questionId
);


}
