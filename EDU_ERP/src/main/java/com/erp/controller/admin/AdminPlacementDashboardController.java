package com.erp.controller.admin;

import com.erp.service.CompanyService;
import com.erp.service.PlacementDriveService;
import com.erp.service.StudentApplicationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPlacementDashboardController {

    private final CompanyService companyService;
    private final PlacementDriveService placementDriveService;
    private final StudentApplicationService applicationService;

    public AdminPlacementDashboardController(
            CompanyService companyService,
            PlacementDriveService placementDriveService,
            StudentApplicationService applicationService) {

        this.companyService = companyService;
        this.placementDriveService = placementDriveService;
        this.applicationService = applicationService;
    }

    @GetMapping("/admin/placement-dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "totalCompanies",
                companyService.getTotalCompanies());

        model.addAttribute(
                "totalDrives",
                placementDriveService.getTotalDrives());

        model.addAttribute(
                "totalApplications",
                applicationService.getTotalApplications());

        model.addAttribute(
                "selectedCount",
                applicationService.getSelectedCount());

        model.addAttribute(
                "shortlistedCount",
                applicationService.getShortlistedCount());

        model.addAttribute(
                "rejectedCount",
                applicationService.getRejectedCount());

        model.addAttribute(
                "placementRate",
                applicationService.getPlacementRate());

        model.addAttribute(
                "activePage",
                "placement-dashboard");

        model.addAttribute(
                "content",
                "admin/placement-dashboard");

        return "layout/admin-layout";
    }
}