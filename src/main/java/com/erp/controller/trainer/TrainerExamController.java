package com.erp.controller.trainer;

import com.erp.service.TrainerExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/trainer/exams")
public class TrainerExamController {

    private final TrainerExamService examService;

    public TrainerExamController(TrainerExamService examService) {
        this.examService = examService;
    }

    // ===============================
    // Trainer → Exams List
    // ===============================
    @GetMapping
    public String exams(Model model) {

        model.addAttribute("exams", examService.getMyExams());
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "trainer/exams");

        return "layout/trainer-layout";
    }

    // ===============================
    // Create Exam Page
    // ===============================
    @GetMapping("/create")
    public String createExamForm(Model model) {

        model.addAttribute("batches", examService.getMyBatches());
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "trainer/exam-create");

        return "layout/trainer-layout";
    }

    // ===============================
    // Save Exam
    // ===============================
    @PostMapping("/create")
    public String createExam(

            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int durationMinutes,
            @RequestParam int totalMarks,
            @RequestParam Long batchId,

            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate

    ) {

        examService.createExam(
                title,
                description,
                durationMinutes,
                totalMarks,
                batchId,
                startDate,
                endDate
        );

        return "redirect:/trainer/exams";
    }

    // ===============================
    // Add Questions Page
    // ===============================
    @GetMapping("/{id}/questions")
    public String addQuestions(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("exam", examService.getExam(id));
        model.addAttribute("activePage", "exams");
        model.addAttribute("content", "trainer/exam-questions");

        return "layout/trainer-layout";
    }

    // ===============================
    // Save Question + Options
    // ===============================
    @PostMapping("/{id}/questions")
    public String saveQuestion(
            @PathVariable Long id,
            @RequestParam String questionText,
            @RequestParam int marks,
            @RequestParam(required = false) MultipartFile questionImage,
            @RequestParam String optionA,
            @RequestParam String optionB,
            @RequestParam String optionC,
            @RequestParam String optionD,
            @RequestParam String correctOption
    ) throws IOException {

        examService.addQuestion(
                id,
                questionText,
                marks,
                questionImage,
                optionA,
                optionB,
                optionC,
                optionD,
                correctOption
        );

        return "redirect:/trainer/exams/" + id + "/questions";
    }
    
//    @GetMapping("/{id}/analytics")
//    public String analytics(
//            @PathVariable Long id,
//            Model model
//    ) {
//
//        model.addAttribute(
//                "exam",
//                examService.getExam(id)
//        );
//
//        model.addAttribute(
//                "analytics",
//                examService.getQuestionAnalytics(id)
//        );
//
//        model.addAttribute(
//                "activePage",
//                "exams"
//        );
//
//        model.addAttribute(
//                "content",
//                "trainer/exam-analytics"
//        );
//
//        return "layout/trainer-layout";
//    }
    
    @GetMapping("/{id}/analytics")
    public String analytics(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "exam",
                examService.getExam(id));

        model.addAttribute(
                "analytics",
                examService.getQuestionAnalytics(id));

        model.addAttribute(
                "summary",
                examService.getExamAnalytics(id));

        model.addAttribute(
                "content",
                "trainer/exam-analytics");

        return "layout/trainer-layout";
    }
    
    @PostMapping("/{id}/toggle-status")
    public String toggleStatus(
            @PathVariable Long id
    ) {

        examService.toggleExamStatus(id);

        return "redirect:/trainer/exams";
    }
    
    @PostMapping("/{id}/publish")
    public String publishExam(
            @PathVariable Long id) {

        examService.publishExam(id);

        return "redirect:/trainer/exams";
    }

    @PostMapping("/{id}/unpublish")
    public String unpublishExam(
            @PathVariable Long id) {

        examService.unpublishExam(id);

        return "redirect:/trainer/exams";
    }
    
    @GetMapping("/{id}/edit")
    public String editExamPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "exam",
                examService.getExam(id));

        model.addAttribute(
                "batches",
                examService.getMyBatches());

        model.addAttribute(
                "content",
                "trainer/exam-edit");

        return "layout/trainer-layout";
    }
    @PostMapping("/{id}/edit")
    public String updateExam(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int durationMinutes,
            @RequestParam int totalMarks,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long batchId
    ) {

        examService.updateExam(
                id,
                title,
                description,
                durationMinutes,
                totalMarks,
                startDate,
                endDate,
                batchId
        );

        return "redirect:/trainer/exams";
    }
    @PostMapping("/questions/{id}/update")
    public String updateQuestion(
            @PathVariable Long id,

            @RequestParam String text,
            @RequestParam int marks,

            @RequestParam String optionA,
            @RequestParam String optionB,
            @RequestParam String optionC,
            @RequestParam String optionD,

            @RequestParam String correctOption
    ){

        examService.updateQuestion(
                id,
                text,
                marks,
                optionA,
                optionB,
                optionC,
                optionD,
                correctOption
        );


        return "redirect:/trainer/exams";
    }
    
    @PostMapping("/questions/{id}/delete")
    public String deleteQuestion(
            @PathVariable Long id
    ){

        examService.deleteQuestion(id);

        return "redirect:/trainer/exams";
    }
}
