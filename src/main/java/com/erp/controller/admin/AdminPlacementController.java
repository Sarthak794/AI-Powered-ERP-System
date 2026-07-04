package com.erp.controller.admin;

import com.erp.entity.Company;
import com.erp.entity.PlacementDrive;
import com.erp.service.AdminQualificationService;
import com.erp.service.AdminSpecializationService;
import com.erp.service.CompanyService;
import com.erp.service.PlacementDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminPlacementController {

    private final AdminQualificationService adminQualificationService;
    private final AdminSpecializationService adminSpecializationService;

    private final CompanyService companyService;
    private final PlacementDriveService placementDriveService;

    public AdminPlacementController(
            CompanyService companyService,
            PlacementDriveService placementDriveService,
            AdminQualificationService adminQualificationService,
            AdminSpecializationService adminSpecializationService) {

        this.companyService = companyService;
        this.placementDriveService = placementDriveService;
        this.adminQualificationService = adminQualificationService;
        this.adminSpecializationService = adminSpecializationService;
    }

    @GetMapping("/companies")
    public String companies(Model model) {

        model.addAttribute("companies",
                companyService.getAllCompanies());

        model.addAttribute("company",
                new Company());

        model.addAttribute("activePage", "companies");
        model.addAttribute("content", "admin/companies");

        return "layout/admin-layout";
    }

    @PostMapping("/companies/save")
    public String saveCompany(@ModelAttribute Company company) {

        companyService.saveCompany(company);

        return "redirect:/admin/companies";
    }

    @PostMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);

        return "redirect:/admin/companies";
    }

    @GetMapping("/placement-drives")
    public String placementDrives(Model model) {

        model.addAttribute("drives",
                placementDriveService.getAllDrives());

        model.addAttribute("companies",
                companyService.getAllCompanies());

        model.addAttribute("drive",
                new PlacementDrive());

        model.addAttribute("activePage", "placement-drives");
        model.addAttribute("content",
                "admin/placement-drives");

        model.addAttribute(
                "qualifications",
                adminQualificationService.getAllQualifications());

        model.addAttribute(
                "specializations",
                adminSpecializationService.getAllSpecializations());

        return "layout/admin-layout";
    }

    @PostMapping("/placement-drives/save")
    public String saveDrive(
            @RequestParam Long companyId,
            @ModelAttribute PlacementDrive drive) {

        Company company =
                companyService.getCompanyById(companyId);

        drive.setCompany(company);

        placementDriveService.saveDrive(drive);

        return "redirect:/admin/placement-drives";
    }

    @PostMapping("/placement-drives/delete/{id}")
    public String deleteDrive(@PathVariable Long id) {

        placementDriveService.deleteDrive(id);

        return "redirect:/admin/placement-drives";
    }
}