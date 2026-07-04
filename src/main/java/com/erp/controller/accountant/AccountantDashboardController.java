package com.erp.controller.accountant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountantDashboardController {

    @GetMapping("/accountant/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalStudents", 0); // later dynamic
        model.addAttribute("totalCollected", 0);
        model.addAttribute("pendingAmount", 0);

        model.addAttribute("activePage", "dashboard");
        model.addAttribute("content", "accountant/dashboard");

        return "layout/accountant-layout";
    }
}
