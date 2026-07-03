package com.erp.service;

import com.erp.entity.Exam;
import com.erp.entity.ExamAttempt;
import com.erp.repository.ExamAttemptRepository;
import com.erp.repository.ExamRepository;
import com.erp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminExamAnalyticsService {

    private final ExamRepository examRepository;
    private final ExamAttemptRepository attemptRepository;
    private final StudentRepository studentRepository;

    public AdminExamAnalyticsService(
            ExamRepository examRepository,
            ExamAttemptRepository attemptRepository,
            StudentRepository studentRepository
    ) {
        this.examRepository = examRepository;
        this.attemptRepository = attemptRepository;
        this.studentRepository = studentRepository;
    }

    public List<Map<String, Object>> getExamAnalytics() {

        List<Map<String, Object>> result = new ArrayList<>();

        for (Exam exam : examRepository.findAll()) {

            List<ExamAttempt> attempts =
                    attemptRepository.findAll()
                            .stream()
                            .filter(a -> a.getExam().getId().equals(exam.getId()))
                            .toList();

            int attempted = attempts.size();

            int totalStudents =
                    (int) studentRepository.countByBatchId(
                            exam.getBatch().getId()
                    );

            double avg = attempts.stream()
                    .mapToInt(ExamAttempt::getScore)
                    .average()
                    .orElse(0);

            int max = attempts.stream()
                    .mapToInt(ExamAttempt::getScore)
                    .max()
                    .orElse(0);

            int min = attempts.stream()
                    .mapToInt(ExamAttempt::getScore)
                    .min()
                    .orElse(0);

            Map<String, Object> map = new HashMap<>();
            map.put("exam", exam.getTitle());
            map.put("attempted", attempted);
            map.put("notAttempted", totalStudents - attempted);
            map.put("average", Math.round(avg * 100.0) / 100.0);
            map.put("max", max);
            map.put("min", min);

            result.add(map);
        }

        return result;
    }
}
