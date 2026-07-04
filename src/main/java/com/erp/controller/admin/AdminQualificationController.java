package com.erp.controller.admin;

import com.erp.service.AdminQualificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminQualificationController {

    private final AdminQualificationService qualificationService;

    public AdminQualificationController(AdminQualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @GetMapping("/qualifications")
    public String qualifications(Model model) {

        model.addAttribute("qualifications", qualificationService.getAllQualifications());

        model.addAttribute("activePage", "qualifications");
        model.addAttribute("content", "admin/qualifications");

        return "layout/admin-layout";
    }

    @PostMapping("/qualifications/create")
    public String createQualification(
            @RequestParam String name,
            RedirectAttributes ra
    ) {
        try {
            qualificationService.createQualification(name);
            ra.addFlashAttribute("success", "Qualification added successfully");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/qualifications";
    }

    @PostMapping("/qualifications/delete/{id}")
    public String deleteQualification(@PathVariable Long id) {
        qualificationService.deleteQualification(id);
        return "redirect:/admin/qualifications";
    }
}
