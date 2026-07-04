package com.erp.controller.accountant;

import com.erp.service.AccountantSalaryPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accountant/salary")
public class AccountantSalaryController {

    private final AccountantSalaryPaymentService salaryService;

    public AccountantSalaryController(
            AccountantSalaryPaymentService salaryService
    ) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public String salaryPage(Model model) {

        model.addAttribute("salaries", salaryService.getAllTrainerSalaries());
        model.addAttribute("payments", salaryService.getAllPayments());

        model.addAttribute("activePage", "salary");
        model.addAttribute("content", "accountant/salary");

        return "layout/accountant-layout";
    }

    @PostMapping("/pay")
    public String paySalary(
            @RequestParam Long trainerId,
            @RequestParam int amount,
            @RequestParam String month,
            RedirectAttributes ra
    ) {
        try {
            salaryService.paySalary(trainerId, amount, month);
            ra.addFlashAttribute("success", "Salary paid successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accountant/salary";
    }
}
