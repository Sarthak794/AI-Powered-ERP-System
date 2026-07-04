package com.erp.controller.admin;

import com.erp.service.AdminStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {

    private final AdminStudentService studentService;

    public AdminStudentController(AdminStudentService studentService) {
        this.studentService = studentService;
    }

    // ✅ CORRECT URL → /admin/students
    @GetMapping("/students")
    public String students(Model model) {

        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("centers", studentService.getAllCenters());
        model.addAttribute("qualifications", studentService.getAllQualifications());
        model.addAttribute("specializations", studentService.getAllSpecializations());

        model.addAttribute("activePage", "students");
        model.addAttribute("content", "admin/students");

        return "layout/admin-layout";
    }

    @PostMapping("/students/create")
    public String createStudent(
            @RequestParam String fullName,
            @RequestParam String rollNumber,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Long centerId,
            @RequestParam Long qualificationId,
            @RequestParam Long specializationId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            studentService.createStudent(
                    fullName, rollNumber, email, phone,
                    username, password,
                    centerId, qualificationId, specializationId
            );
            redirectAttributes.addFlashAttribute("success", "Student added successfully");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/students";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }
}
