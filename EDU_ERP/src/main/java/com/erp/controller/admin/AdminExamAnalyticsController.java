package com.erp.controller.admin;

import com.erp.service.AdminExamAnalyticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminExamAnalyticsController {

    private final AdminExamAnalyticsService analyticsService;

    public AdminExamAnalyticsController(
            AdminExamAnalyticsService analyticsService
    ) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/admin/exam-analytics")
    public String analytics(Model model) {

        model.addAttribute(
                "analytics",
                analyticsService.getExamAnalytics()
        );

        model.addAttribute("activePage", "analytics");
        model.addAttribute("content", "admin/exam-analytics");

        return "layout/admin-layout";
    }
}
