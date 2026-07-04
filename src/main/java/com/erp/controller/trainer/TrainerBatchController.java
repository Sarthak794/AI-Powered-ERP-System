package com.erp.controller.trainer;

import com.erp.service.TrainerBatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/trainer")
public class TrainerBatchController {

    private final TrainerBatchService service;

    public TrainerBatchController(TrainerBatchService service) {
        this.service = service;
    }

    // STEP 5
    @GetMapping("/batches/{id}/students")
    public String students(@PathVariable Long id, Model model) {

        model.addAttribute("students", service.getBatchStudents(id));
        model.addAttribute("content", "trainer/students");

        return "layout/trainer-layout";
    }

    // STEP 6
    @GetMapping("/batches/{id}/performance")
    public String performance(@PathVariable Long id, Model model) {

        model.addAttribute("performance", service.getBatchPerformance(id));
        model.addAttribute("content", "trainer/performance");

        return "layout/trainer-layout";
    }

    // STEP 7 KPI
    @GetMapping("/dashboard/kpis")
    public String kpis(Model model) {

        model.addAttribute("kpis", service.getKpis());
        return "trainer/kpis";
    }
}