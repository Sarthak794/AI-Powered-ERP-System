package com.erp.controller.admin;

import com.erp.service.AdminFeeService;
import com.erp.service.AdminStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/fees")
public class AdminFeeController {

    private final AdminFeeService feeService;
    private final AdminStudentService studentService;

    public AdminFeeController(
            AdminFeeService feeService,
            AdminStudentService studentService
    ) {
        this.feeService = feeService;
        this.studentService = studentService;
    }

    @GetMapping
    public String fees(Model model) {

        model.addAttribute("fees", feeService.getAllFees());
        model.addAttribute("students", studentService.getAllStudents());

        model.addAttribute("activePage", "fees");
        model.addAttribute("content", "admin/fees");

        return "layout/admin-layout";
    }

    @PostMapping("/allot")
    public String allotFee(
            @RequestParam Long studentId,
            @RequestParam int totalAmount,
            RedirectAttributes ra
    ) {
        try {
            feeService.allotFee(studentId, totalAmount);
            ra.addFlashAttribute("success", "Fee allotted successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fees";
    }
}
