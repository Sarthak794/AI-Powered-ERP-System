package com.erp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.entity.Exam;
import com.erp.entity.ExamAnswer;
import com.erp.entity.ExamAttempt;
import com.erp.entity.ExamProgress;
import com.erp.entity.Option;
import com.erp.entity.Question;
import com.erp.entity.Student;
import com.erp.repository.ExamAnswerRepository;
import com.erp.repository.ExamAttemptRepository;
import com.erp.repository.ExamProgressRepository;
import com.erp.repository.ExamRepository;
import com.erp.repository.OptionRepository;
import com.erp.dto.ExamReviewDTO;
import java.util.ArrayList;

@Service
public class StudentExamService {

    private final ExamRepository examRepository;
    private final ExamAttemptRepository attemptRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final OptionRepository optionRepository;
    private final StudentService studentService;
    private final ExamProgressRepository progressRepository;

    public StudentExamService(
            ExamRepository examRepository,
            ExamAttemptRepository attemptRepository,
            ExamAnswerRepository examAnswerRepository,
            OptionRepository optionRepository,
            StudentService studentService,
            ExamProgressRepository progressRepository
    ) {
        this.examRepository = examRepository;
        this.attemptRepository = attemptRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.optionRepository = optionRepository;
        this.studentService = studentService;
        this.progressRepository = progressRepository;
    }

    // ===============================
    // Available Exams
    // ===============================
    public List<Exam> getAvailableExams() {

        Student student = studentService.getCurrentStudent();

        return examRepository.findByBatchAndActiveTrue(
                student.getBatch()
        );
    }

    // ===============================
    // Prevent Reattempt
    // ===============================
    public boolean hasAttempted(Long examId) {

        Student student = studentService.getCurrentStudent();

        return attemptRepository.existsByExamIdAndStudentId(
                examId,
                student.getId()
        );
    }

    // ===============================
    // Get Exam with Questions
    // ===============================
    public Exam getExamWithQuestions(Long examId) {

        Student student = studentService.getCurrentStudent();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        if (!exam.getBatch().equals(student.getBatch())) {
            throw new RuntimeException(
                    "You are not allowed to access this exam"
            );
        }

        // Force load questions and options
        exam.getQuestions().forEach(
                q -> q.getOptions().size()
        );

        return exam;
    }

    // ===============================
    // Submit Exam
    // ===============================
    @Transactional
    public void submitExam(
            Long examId,
            Map<String, String> params
    ) {

        Student student =
                studentService.getCurrentStudent();

        if (hasAttempted(examId)) {

            throw new IllegalStateException(
                    "Exam already attempted"
            );
        }

        Exam exam =
                examRepository.findById(examId)
                        .orElseThrow();

        ExamAttempt attempt =
                new ExamAttempt();

        attempt.setExam(exam);
        attempt.setStudent(student);
        attempt.setSubmittedAt(
                LocalDateTime.now()
        );

        attempt.setScore(0);

        attempt =
                attemptRepository.save(attempt);

        int score = 0;

        List<ExamProgress> progressList =
                progressRepository
                        .findByExamIdAndStudentId(
                                examId,
                                student.getId()
                        );

        for (ExamProgress progress : progressList) {

            if (progress.getSelectedOption() == null) {
                continue;
            }

            ExamAnswer answer =
                    new ExamAnswer();

            answer.setAttempt(attempt);

            answer.setQuestion(
                    progress.getQuestion()
            );

            answer.setSelectedOption(
                    progress.getSelectedOption()
            );

            examAnswerRepository.save(answer);

            if (
                progress
                    .getSelectedOption()
                    .isCorrect()
            ) {

                score +=
                        progress
                        .getQuestion()
                        .getMarks();
            }
        }

        attempt.setScore(score);

        attemptRepository.save(attempt);

        progressRepository
                .deleteByExamIdAndStudentId(
                        examId,
                        student.getId()
                );
    }
    // ===============================
    // Result
    // ===============================
    public ExamAttempt getMyAttempt(Long examId) {

        Student student = studentService.getCurrentStudent();

        return attemptRepository.findByExamIdAndStudentId(
                examId,
                student.getId()
        );
    }

    // ===============================
    // Exam History
    // ===============================
    public List<ExamAttempt> getMyAttempts() {

        Student student = studentService.getCurrentStudent();

        return attemptRepository.findAll()
                .stream()
                .filter(a ->
                        a.getStudent()
                                .getId()
                                .equals(student.getId()))
                .toList();
    }
    
    public List<ExamReviewDTO> getExamReview(Long examId) {

        ExamAttempt attempt = getMyAttempt(examId);

        List<ExamAnswer> answers =
                examAnswerRepository.findByAttemptId(
                        attempt.getId()
                );

        List<ExamReviewDTO> review =
                new ArrayList<>();

        for (Question question :
                attempt.getExam().getQuestions()) {

            ExamAnswer answer =
                    answers.stream()
                            .filter(a ->
                                    a.getQuestion()
                                     .getId()
                                     .equals(question.getId()))
                            .findFirst()
                            .orElse(null);

            String selectedAnswer =
                    "Not Answered";

            if (answer != null) {
                selectedAnswer =
                        answer.getSelectedOption()
                              .getText();
            }

            String correctAnswer =
                    question.getOptions()
                            .stream()
                            .filter(Option::isCorrect)
                            .findFirst()
                            .map(Option::getText)
                            .orElse("-");

            boolean correct =
                    answer != null
                    && answer.getSelectedOption()
                             .isCorrect();

            review.add(
                    new ExamReviewDTO(
                            question.getText(),
                            selectedAnswer,
                            correctAnswer,
                            correct
                    )
            );
        }

        return review;
    }
    
    public void saveAnswer(
            Long examId,
            Long questionId,
            Long optionId
    ) {

        Student student =
                studentService.getCurrentStudent();

        ExamProgress progress =
                progressRepository
                .findByExamIdAndStudentIdAndQuestionId(
                        examId,
                        student.getId(),
                        questionId
                )
                .orElse(new ExamProgress());

        progress.setExam(
                examRepository.findById(examId)
                        .orElseThrow()
        );

        progress.setStudent(student);

        Question question =
                examRepository.findById(examId)
                        .orElseThrow()
                        .getQuestions()
                        .stream()
                        .filter(q ->
                                q.getId().equals(questionId))
                        .findFirst()
                        .orElseThrow();

        progress.setQuestion(question);

        progress.setSelectedOption(
                optionRepository.findById(optionId)
                        .orElseThrow()
        );

        progress.setSavedAt(
                java.time.LocalDateTime.now()
        );

        progressRepository.save(progress);
    }
    
    public List<ExamProgress> getSavedProgress(Long examId) {

        Student student =
                studentService.getCurrentStudent();

        return progressRepository
                .findByExamIdAndStudentId(
                        examId,
                        student.getId()
                );
    }
    
    public void markReview(
            Long examId,
            Long questionId,
            boolean review
    ) {

        Student student =
                studentService.getCurrentStudent();

        ExamProgress progress =
                progressRepository
                        .findByExamIdAndStudentIdAndQuestionId(
                                examId,
                                student.getId(),
                                questionId
                        )
                        .orElse(null);

        if (progress == null) {
            return;
        }

        progress.setReviewFlag(review);

        progressRepository.save(progress);
    }
}