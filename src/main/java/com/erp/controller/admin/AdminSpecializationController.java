package com.erp.controller.admin;

import com.erp.service.AdminSpecializationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminSpecializationController {

    private final AdminSpecializationService specializationService;

    public AdminSpecializationController(AdminSpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/specializations")
    public String specializations(Model model) {

        model.addAttribute("specializations", specializationService.getAllSpecializations());

        model.addAttribute("activePage", "specializations");
        model.addAttribute("content", "admin/specializations");

        return "layout/admin-layout";
    }

    @PostMapping("/specializations/create")
    public String createSpecialization(
            @RequestParam String name,
            RedirectAttributes ra
    ) {
        try {
            specializationService.createSpecialization(name);
            ra.addFlashAttribute("success", "Specialization added successfully");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/specializations";
    }

    @PostMapping("/specializations/delete/{id}")
    public String deleteSpecialization(@PathVariable Long id) {
        specializationService.deleteSpecialization(id);
        return "redirect:/admin/specializations";
    }
}
