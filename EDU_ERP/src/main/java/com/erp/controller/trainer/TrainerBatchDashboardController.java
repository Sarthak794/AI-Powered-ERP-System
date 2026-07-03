package com.erp.controller.trainer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.dto.TrainerBatchDashboardDto;
import com.erp.dto.TrainerKpiDto;
import com.erp.service.TrainerBatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/trainer")
public class TrainerBatchDashboardController {

    private final TrainerBatchService trainerBatchService;

    public TrainerBatchDashboardController(TrainerBatchService trainerBatchService) {
        this.trainerBatchService = trainerBatchService;
    }

    @GetMapping("/batches")
    public String batches(Model model) {

        List<TrainerBatchDashboardDto> batches =
                trainerBatchService.getTrainerBatchDashboard();

        System.out.println("Batches sent to UI = " + batches.size());

        model.addAttribute("batches", batches);

        model.addAttribute("activePage", "batches");
        model.addAttribute("content", "trainer/batches");

        return "layout/trainer-layout";
    }
}