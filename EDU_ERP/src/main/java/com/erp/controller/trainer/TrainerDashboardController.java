package com.erp.controller.trainer;

import com.erp.service.TrainerAttendanceService;
import com.erp.service.TrainerDashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/trainer")
public class TrainerDashboardController {

    private final TrainerDashboardService dashboardService;

    public TrainerDashboardController(
            TrainerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("trainer", dashboardService.getTrainer()); // 🔥 REQUIRED

        model.addAttribute("totalBatches", dashboardService.getBatchCount());
        model.addAttribute("totalStudents", dashboardService.getStudentCount());
        model.addAttribute("avgAttendance", dashboardService.getAverageAttendance());
        model.addAttribute("batches", dashboardService.getMyBatches());

        model.addAttribute("totalAssignments", dashboardService.getTotalAssignments());
        model.addAttribute("totalSubmissions", dashboardService.getTotalSubmissions());
        model.addAttribute("pendingReviews", dashboardService.getPendingReviews());

        model.addAttribute("content", "trainer/dashboard");

        return "layout/trainer-layout";
    }
}