package com.erp.controller.admin;

import com.erp.service.AdminTrainerService;
import com.erp.service.AdminStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminTrainerController {

    private final AdminTrainerService trainerService;
    private final AdminStudentService studentService;

    public AdminTrainerController(
            AdminTrainerService trainerService,
            AdminStudentService studentService
    ) {
        this.trainerService = trainerService;
        this.studentService = studentService;
    }

    // ✅ URL → /admin/trainers
    @GetMapping("/trainers")
    public String trainersPage(Model model) {

        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("centers", studentService.getAllCenters());
        model.addAttribute("specializations", studentService.getAllSpecializations());

        model.addAttribute("activePage", "trainers");
        model.addAttribute("content", "admin/trainers");

        return "layout/admin-layout";
    }

    @PostMapping("/trainers/create")
    public String createTrainer(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Long centerId,
            @RequestParam Long specializationId,
            RedirectAttributes ra
    ) {
        try {
            trainerService.createTrainer(
                    fullName, email, phone,
                    username, password,
                    centerId, specializationId
            );
            ra.addFlashAttribute("success", "Trainer added successfully");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/trainers";
    }

    @PostMapping("/trainers/delete/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return "redirect:/admin/trainers";
    }
}
