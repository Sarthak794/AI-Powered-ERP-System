package com.erp.controller.trainer;

import com.erp.service.TrainerSalaryViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainerSalaryController {

    private final TrainerSalaryViewService salaryService;

    public TrainerSalaryController(
            TrainerSalaryViewService salaryService
    ) {
        this.salaryService = salaryService;
    }

    @GetMapping("/trainer/salary")
    public String salary(Model model) {

        model.addAttribute("payments", salaryService.getMySalaryHistory());

        model.addAttribute("activePage", "salary");
        model.addAttribute("content", "trainer/salary");

        return "layout/trainer-layout";
    }
}
