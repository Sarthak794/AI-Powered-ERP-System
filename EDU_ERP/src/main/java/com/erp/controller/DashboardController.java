package com.erp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            if (role.equals("ADMIN")) {
                return "redirect:/admin/dashboard";
            }
            if (role.equals("STUDENT")) {
                return "redirect:/student/dashboard";
            }
            if (role.equals("TRAINER")) {
                return "redirect:/trainer/dashboard";
            }
            if (role.equals("MANAGER")) {
                return "redirect:/manager/dashboard";
            }
            if (role.equals("ACCOUNTANT")) {
                return "redirect:/accountant/dashboard";
            }
        }

        // fallback (should not happen)
        return "redirect:/login?error";
    }
}
