package com.erp.controller.admin;

import com.erp.service.AdminCenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminCenterController {

    private final AdminCenterService centerService;

    public AdminCenterController(AdminCenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping("/centers")
    public String centers(Model model) {

        model.addAttribute("centers", centerService.getAllCenters());

        model.addAttribute("activePage", "centers");
        model.addAttribute("content", "admin/centers");

        return "layout/admin-layout";
    }

    @PostMapping("/centers/create")
    public String createCenter(
            @RequestParam String name,
            RedirectAttributes ra
    ) {
        try {
            centerService.createCenter(name);
            ra.addFlashAttribute("success", "Center added successfully");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/centers";
    }

    @PostMapping("/centers/delete/{id}")
    public String deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return "redirect:/admin/centers";
    }
}
