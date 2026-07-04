package com.erp.controller.admin;

import com.erp.service.AdminDashboardService;
import com.erp.service.StudentApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    private final StudentApplicationService applicationService;
    private final ObjectMapper mapper;

    public AdminDashboardController(
            AdminDashboardService dashboardService,
            StudentApplicationService applicationService,
            ObjectMapper mapper) {

        this.dashboardService = dashboardService;
        this.applicationService = applicationService;
        this.mapper = mapper;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) throws JsonProcessingException {

        /* =====================================================
                         USER KPI
        ===================================================== */

        model.addAttribute("totalUsers",
                dashboardService.totalUsers());

        model.addAttribute("totalStudents",
                dashboardService.totalStudents());

        model.addAttribute("totalTrainers",
                dashboardService.totalTrainers());

        model.addAttribute("totalAdmins",
                dashboardService.totalAdmins());

        model.addAttribute("totalManagers",
                dashboardService.totalManagers());

        model.addAttribute("totalAccountants",
                dashboardService.totalAccountants());


        /* =====================================================
                     PLACEMENT KPI
        ===================================================== */

        model.addAttribute("placedStudents",
                dashboardService.getPlacedStudents());

        model.addAttribute("placementRate",
                applicationService.getPlacementRate());

        model.addAttribute("activeDrives",
                dashboardService.getActiveDrives());

        model.addAttribute("highestPackage",
                dashboardService.getHighestPackage());

        model.addAttribute("pendingApplications",
                dashboardService.getPendingApplications());

        model.addAttribute("companiesVisited",
                dashboardService.getCompaniesVisited());


        /* =====================================================
                     ANALYTICS CARDS
        ===================================================== */

        model.addAttribute("topCompany",
                dashboardService.getTopCompany());

        model.addAttribute("topBatch",
                dashboardService.getTopBatch());

        model.addAttribute("avgPackage",
                dashboardService.getAvgPackage());

        model.addAttribute("placementGrowth",
                dashboardService.getPlacementGrowth());


        /* =====================================================
                     CHART DATA
        ===================================================== */

        model.addAttribute(
                "monthsJson",
                toJson(extract(applicationService.getMonthlyPlacements(), 0))
        );

        model.addAttribute(
                "placementsJson",
                toJson(extract(applicationService.getMonthlyPlacements(), 1))
        );

        model.addAttribute(
                "companiesJson",
                toJson(extract(applicationService.getCompanyWisePlacements(), 0))
        );

        model.addAttribute(
                "companyPlacementsJson",
                toJson(extract(applicationService.getCompanyWisePlacements(), 1))
        );

        model.addAttribute(
                "batchesJson",
                toJson(extract(applicationService.getBatchWisePlacements(), 0))
        );

        model.addAttribute(
                "batchRatesJson",
                toJson(extract(applicationService.getBatchWisePlacements(), 1))
        );


        /* =====================================================
                     LAYOUT
        ===================================================== */

        model.addAttribute("activePage", "dashboard");
        model.addAttribute("content", "admin/dashboard");

        return "layout/admin-layout";
    }

    /* =====================================================
                    HELPER METHODS
    ===================================================== */

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    private List<?> extract(List<Object[]> list, int index) {
        return list.stream()
                .map(obj -> obj[index])
                .toList();
    }
}