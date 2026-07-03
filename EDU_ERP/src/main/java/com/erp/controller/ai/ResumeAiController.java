package com.erp.controller.ai;

import com.erp.dto.ResumeAnalysisResponse;
import com.erp.entity.Student;
import com.erp.repository.StudentRepository;
import com.erp.service.ResumeAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/resume")
public class ResumeAiController {

    private final ResumeAiService resumeAiService;
    private final StudentRepository studentRepository;

    public ResumeAiController(ResumeAiService resumeAiService,
                              StudentRepository studentRepository) {

        this.resumeAiService = resumeAiService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/analysis/{studentId}")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(
            @PathVariable Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        ResumeAnalysisResponse response =
                resumeAiService.analyze(student);

        return ResponseEntity.ok(response);
    }

}