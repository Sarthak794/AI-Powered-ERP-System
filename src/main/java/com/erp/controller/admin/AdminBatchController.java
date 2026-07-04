package com.erp.controller.admin;

import com.erp.service.AdminBatchService;
import com.erp.service.AdminStudentService;
import com.erp.service.AdminTrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminBatchController {

    private final AdminBatchService batchService;
    private final AdminStudentService studentService;
    private final AdminTrainerService trainerService;

    public AdminBatchController(
            AdminBatchService batchService,
            AdminStudentService studentService,
            AdminTrainerService trainerService
    ) {
        this.batchService = batchService;
        this.studentService = studentService;
        this.trainerService = trainerService;
    }

    // ✅ URL → /admin/batches
    @GetMapping("/batches")
    public String batchesPage(Model model) {

        model.addAttribute("batches", batchService.getAllBatches());
        model.addAttribute("centers", studentService.getAllCenters());
        model.addAttribute("qualifications", studentService.getAllQualifications());
        model.addAttribute("specializations", studentService.getAllSpecializations());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("students", batchService.getUnassignedStudents());

        model.addAttribute("activePage", "batches");
        model.addAttribute("content", "admin/batches");

        return "layout/admin-layout";
    }

    @PostMapping("/batches/create")
    public String createBatch(
            @RequestParam String name,
            @RequestParam Long centerId,
            @RequestParam Long qualificationId,
            @RequestParam Long specializationId,
            @RequestParam Long trainerId,
            RedirectAttributes ra
    ) {
        try {
            batchService.createBatch(
                    name, centerId, qualificationId, specializationId, trainerId
            );
            ra.addFlashAttribute("success", "Batch created successfully");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/batches";
    }

    @PostMapping("/batches/assign-student")
    public String assignStudent(
            @RequestParam Long studentId,
            @RequestParam Long batchId
    ) {
        batchService.assignStudentToBatch(studentId, batchId);
        return "redirect:/admin/batches";
    }
}
