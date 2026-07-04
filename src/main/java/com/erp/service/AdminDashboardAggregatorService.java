package com.erp.service;

import com.erp.dto.AdminDashboardDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminDashboardAggregatorService {

    private final AdminDashboardService dashboardService;
    private final CompanyService companyService;
    private final PlacementDriveService placementDriveService;
    private final StudentApplicationService applicationService;

    public AdminDashboardAggregatorService(
            AdminDashboardService dashboardService,
            CompanyService companyService,
            PlacementDriveService placementDriveService,
            StudentApplicationService applicationService) {

        this.dashboardService = dashboardService;
        this.companyService = companyService;
        this.placementDriveService = placementDriveService;
        this.applicationService = applicationService;
    }

    public AdminDashboardDTO getDashboard() {

        AdminDashboardDTO dto = new AdminDashboardDTO();

        // EXISTING KPIs
        dto.setTotalUsers(dashboardService.totalUsers());
        dto.setTotalAdmins(dashboardService.totalAdmins());
        dto.setTotalManagers(dashboardService.totalManagers());
        dto.setTotalTrainers(dashboardService.totalTrainers());
        dto.setTotalStudents(dashboardService.totalStudents());
        dto.setTotalAccountants(dashboardService.totalAccountants());

        // PLACEMENT KPIs
        dto.setTotalCompanies(companyService.getTotalCompanies());
        dto.setTotalDrives(placementDriveService.getTotalDrives());
        dto.setTotalApplications(applicationService.getTotalApplications());
        dto.setSelectedCount(applicationService.getSelectedCount());
        dto.setShortlistedCount(applicationService.getShortlistedCount());
        dto.setRejectedCount(applicationService.getRejectedCount());
        dto.setPlacementRate(applicationService.getPlacementRate());

        // SAMPLE ANALYTICS (replace later with DB queries)
        dto.setTopCompany("TCS");
        dto.setTopBatch("Batch A");
        dto.setAvgPackage(5.2);
        dto.setPlacementGrowth(18.5);

        // SAMPLE CHART DATA (IMPORTANT for UI)
        dto.setMonths(Arrays.asList("Jan","Feb","Mar","Apr","May","Jun"));
        dto.setMonthlyPlacements(Arrays.asList(10,15,20,18,25,30));

        dto.setCompanies(Arrays.asList("TCS","Infosys","Wipro","Accenture"));
        dto.setCompanyPlacements(Arrays.asList(12,18,10,22));

        dto.setBatches(Arrays.asList("A","B","C"));
        dto.setBatchSuccessRate(Arrays.asList(80,65,90));

        return dto;
    }
}