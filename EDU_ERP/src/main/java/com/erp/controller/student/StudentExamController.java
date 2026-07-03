package com.erp.controller.student;

import com.erp.entity.ExamAttempt;
import com.erp.service.StudentExamService;
import com.erp.service.StudentResultPdfService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.util.Map;

@Controller
@RequestMapping("/student/exams")
public class StudentExamController {

    private final StudentExamService examService;
    private final StudentResultPdfService pdfService;

    public StudentExamController(
            StudentExamService examService,
            StudentResultPdfService pdfService
    ) {
        this.examService = examService;
        this.pdfService = pdfService;
    }

    // ===============================
    // 1️⃣ View Available Exams
    // ===============================
    @GetMapping
    public String exams(Model model) {

        model.addAttribute("exams", examService.getAvailableExams());
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "student/exams");

        return "layout/student-layout";
    }

    // ===============================
    // 2️⃣ Start Exam
    // ===============================
    @GetMapping("/{id}")
    public String startExam(
            @PathVariable Long id,
            Model model,
            RedirectAttributes ra
    ) {

        if (examService.hasAttempted(id)) {
            ra.addFlashAttribute(
                    "error",
                    "You have already attempted this exam"
            );
            return "redirect:/student/exams";
        }

        model.addAttribute(
                "exam",
                examService.getExamWithQuestions(id)
        );

        model.addAttribute(
                "savedAnswers",
                examService.getSavedProgress(id)
        );

        model.addAttribute(
                "activePage",
                "exams"
        );

        model.addAttribute(
                "content",
                "student/exam-attempt"
        );

        return "layout/student-layout";
    }
    // ===============================
    // 3️⃣ Submit Exam
    // ===============================
//    @PostMapping("/submit")
//    public String submitExam(
//            @RequestParam Long examId,
//            @RequestParam Map<Long, Long> answers,
//            RedirectAttributes ra
//    ) {
//
//        examService.submitExam(examId, answers);
//        ra.addFlashAttribute("success", "Exam submitted successfully");
//
//        return "redirect:/student/exams/result/" + examId;
//    }
    @PostMapping("/submit")
    public String submitExam(
            @RequestParam Map<String, String> params,
            RedirectAttributes ra
    ) {

        Long examId = Long.parseLong(
                params.get("examId")
        );

        examService.submitExam(
                examId,
                params
        );

        ra.addFlashAttribute(
                "success",
                "Exam submitted successfully"
        );

        return "redirect:/student/exams/result/" + examId;
    }
    // ===============================
    // 4️⃣ View Result
    // ===============================
    @GetMapping("/result/{id}")
    public String viewResult(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute("attempt", examService.getMyAttempt(id));
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "student/exam-result");

        return "layout/student-layout";
    }

    // ===============================
    // 5️⃣ Exam History
    // ===============================
    @GetMapping("/history")
    public String history(Model model) {

        model.addAttribute("attempts", examService.getMyAttempts());
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "student/exam-history");

        return "layout/student-layout";
    }

    // ===============================
    // 6️⃣ Violation Logging
    // ===============================
    @PostMapping("/violation")
    @ResponseBody
    public void logViolation(
            @RequestBody String payload
    ) {
        System.out.println(payload);
    }
    // ===============================
    // 7️⃣ Download Result PDF
    // ===============================
    @GetMapping("/result/{id}/pdf")
    public ResponseEntity<InputStreamResource> downloadPdf(
            @PathVariable Long id
    ) {

        ExamAttempt attempt = examService.getMyAttempt(id);
        ByteArrayInputStream pdf = pdfService.generateScorecard(attempt);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=scorecard.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    
    @GetMapping("/review/{id}")
    public String reviewExam(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "review",
                examService.getExamReview(id)
        );

        model.addAttribute(
                "attempt",
                examService.getMyAttempt(id)
        );

        model.addAttribute(
                "activePage",
                "exams"
        );

        model.addAttribute(
                "content",
                "student/exam-review"
        );

        return "layout/student-layout";
    }
    
    @PostMapping("/save-answer")
    @ResponseBody
    public String saveAnswer(
            @RequestParam Long examId,
            @RequestParam Long questionId,
            @RequestParam Long optionId
    ) {

        System.out.println(
            "SAVE => exam=" + examId +
            " question=" + questionId +
            " option=" + optionId
        );

        examService.saveAnswer(
                examId,
                questionId,
                optionId
        );

        return "saved";
    }
    @PostMapping("/mark-review")
    @ResponseBody
    public String markReview(
            @RequestParam Long examId,
            @RequestParam Long questionId,
            @RequestParam boolean review
    ) {

        examService.markReview(
                examId,
                questionId,
                review
        );

        return "ok";
    }
}
