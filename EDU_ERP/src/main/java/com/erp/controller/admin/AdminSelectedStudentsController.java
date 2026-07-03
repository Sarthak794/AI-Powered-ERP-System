package com.erp.controller.admin;

import com.erp.service.StudentApplicationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminSelectedStudentsController {

    private final StudentApplicationService applicationService;

    public AdminSelectedStudentsController(
            StudentApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @GetMapping("/admin/selected-students")
    public String selectedStudents(
            Model model) {

        model.addAttribute(
                "students",
                applicationService
                        .getSelectedStudents());

        model.addAttribute(
                "activePage",
                "selected-students");

        model.addAttribute(
                "content",
                "admin/selected-students");

        return "layout/admin-layout";
    }
}