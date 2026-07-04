package com.erp.service;

import com.erp.repository.CompanyRepository;
import com.erp.repository.PlacementDriveRepository;
import com.erp.repository.StudentApplicationRepository;
import com.erp.repository.UserRepository;
import com.erp.enums.Role;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final StudentApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final PlacementDriveRepository placementDriveRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            StudentApplicationRepository applicationRepository,
            CompanyRepository companyRepository,
            PlacementDriveRepository placementDriveRepository) {

        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
        this.placementDriveRepository = placementDriveRepository;
    }

    /* =====================================================
                        USER KPI
    ===================================================== */

    public long totalUsers() {
        return userRepository.count();
    }

    public long totalStudents() {
        return userRepository.countByRole(Role.STUDENT);
    }

    public long totalTrainers() {
        return userRepository.countByRole(Role.TRAINER);
    }

    public long totalAdmins() {
        return userRepository.countByRole(Role.ADMIN);
    }

    public long totalManagers() {
        return userRepository.countByRole(Role.MANAGER);
    }

    public long totalAccountants() {
        return userRepository.countByRole(Role.ACCOUNTANT);
    }

    /* =====================================================
                    PLACEMENT KPI
    ===================================================== */

    public long getPlacedStudents() {
        return applicationRepository.countSelected();
    }

    public long getPendingApplications() {
        return applicationRepository.countShortlisted();
    }

    public long getCompaniesVisited() {
        return companyRepository.count();
    }

    public long getActiveDrives() {
        return placementDriveRepository.countByActiveTrue();
    }

    public double getHighestPackage() {

        Double value = placementDriveRepository.getHighestPackage();

        return value == null ? 0.0 : value;
    }

    public double getAvgPackage() {

        Double value = placementDriveRepository.getAveragePackage();

        return value == null
                ? 0.0
                : Math.round(value * 100.0) / 100.0;
    }

    /* =====================================================
                        TOP COMPANY
    ===================================================== */

    public String getTopCompany() {

        List<Object[]> list =
                applicationRepository.getTopCompany();

        if (list.isEmpty()) {
            return "-";
        }

        return String.valueOf(list.get(0)[0]);
    }

    /* =====================================================
                        TOP BATCH
    ===================================================== */

    public String getTopBatch() {

        List<Object[]> list =
                applicationRepository.getTopBatch();

        if (list.isEmpty()) {
            return "-";
        }

        return String.valueOf(list.get(0)[0]);
    }

    /* =====================================================
                    PLACEMENT GROWTH
    ===================================================== */

    public double getPlacementGrowth() {

        List<Object[]> monthly =
                applicationRepository.getMonthlyPlacements();

        if (monthly.size() < 2) {
            return 0.0;
        }

        Object[] current =
                monthly.get(monthly.size() - 1);

        Object[] previous =
                monthly.get(monthly.size() - 2);

        long currentCount =
                ((Number) current[1]).longValue();

        long previousCount =
                ((Number) previous[1]).longValue();

        if (previousCount == 0) {
            return currentCount > 0 ? 100.0 : 0.0;
        }

        double growth =
                ((currentCount - previousCount) * 100.0)
                        / previousCount;

        return Math.round(growth * 100.0) / 100.0;
    }

    /* =====================================================
                    CHART DATA
    ===================================================== */

    public List<Object[]> getMonthlyPlacements() {
        return applicationRepository.getMonthlyPlacements();
    }

    public List<Object[]> getCompanyWisePlacements() {
        return applicationRepository.getCompanyWisePlacements();
    }

    public List<Object[]> getBatchWisePlacements() {
        return applicationRepository.getBatchWisePlacements();
    }
}