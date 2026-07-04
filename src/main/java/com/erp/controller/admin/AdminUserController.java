package com.erp.controller.admin;

import com.erp.enums.Role;
import com.erp.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    // ✅ URL → /admin/users
    @GetMapping("/users")
    public String users(Model model) {

        model.addAttribute("users", adminUserService.getAllUsers());
        model.addAttribute("roles", Role.values());

        model.addAttribute("activePage", "users");
        model.addAttribute("content", "admin/users");

        return "layout/admin-layout";
    }

    @PostMapping("/users/toggle/{id}")
    public String toggleUser(@PathVariable Long id) {
        adminUserService.toggleUserStatus(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/create")
    public String createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role
    ) {
        adminUserService.createUser(username, password, role);
        return "redirect:/admin/users";
    }
}
