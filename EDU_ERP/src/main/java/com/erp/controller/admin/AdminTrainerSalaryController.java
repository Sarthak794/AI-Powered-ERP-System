package com.erp.controller.admin;

import com.erp.service.AdminTrainerSalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/trainer-salary")
public class AdminTrainerSalaryController {

    private final AdminTrainerSalaryService salaryService;

    public AdminTrainerSalaryController(
            AdminTrainerSalaryService salaryService
    ) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public String salaryPage(Model model) {

        model.addAttribute("trainers", salaryService.getAllTrainers());
        model.addAttribute("salaries", salaryService.getAllSalaries());

        model.addAttribute("activePage", "trainer-salary");
        model.addAttribute("content", "admin/trainer-salary");

        return "layout/admin-layout";
    }

    @PostMapping("/allot")
    public String allotSalary(
            @RequestParam Long trainerId,
            @RequestParam int monthlyAmount,
            RedirectAttributes ra
    ) {
        try {
            salaryService.allotSalary(trainerId, monthlyAmount);
            ra.addFlashAttribute("success", "Salary allotted successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/trainer-salary";
    }
}
