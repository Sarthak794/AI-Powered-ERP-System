package com.erp.controller.student;

import com.erp.entity.Student;
import com.erp.service.StudentDashboardService;
import com.erp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentProfileController {

    private final StudentService studentService;
    private final StudentDashboardService studentDashboardService;

    public StudentProfileController(
            StudentService studentService,
            StudentDashboardService studentDashboardService) {

        this.studentService = studentService;
        this.studentDashboardService = studentDashboardService;
    }
    @GetMapping("/student/profile")
    public String profile(Model model) {

        Student student = studentService.getCurrentStudent();

        model.addAttribute("student", student);

        model.addAttribute("profileCompletion",
                studentService.getProfileCompletion(student));

        model.addAttribute("resumeUploaded",
                student.getResumePath() != null);

        // =========================
        // FIXED PART (REAL DATA)
        // =========================

        model.addAttribute("attendancePercentage",
                studentService.getAttendancePercentage(student));

        // 🔥 USE DASHBOARD SERVICE HERE (IMPORTANT FIX)
        model.addAttribute("examAverage",
                studentDashboardService.getAverageScore(student));

        model.addAttribute("pendingFees",
                studentDashboardService.getPendingFees(student));

        model.addAttribute("highestScore",
                studentDashboardService.getHighestScore(student));

        model.addAttribute("documentCount",
                studentDashboardService.getDocumentCount(student));

        model.addAttribute("activePage", "profile");
        model.addAttribute("content", "student/profile");

        return "layout/student-layout";
    }

    @GetMapping("/student/profile/edit")
    public String editProfile(Model model) {

        Student student = studentService.getCurrentStudent();

        model.addAttribute("student", student);

        model.addAttribute(
                "profileCompletion",
                studentService.getProfileCompletion(student));

        model.addAttribute("activePage", "profile-edit");
        model.addAttribute("content", "student/profile-edit");

        return "layout/student-layout";
    }

    @PostMapping("/student/profile/update")
    public String updateProfile(
            @ModelAttribute Student formStudent,
            @RequestParam(value = "photo",
                    required = false)
            MultipartFile photo,
            RedirectAttributes ra) {

        studentService.updateStudentProfile(
                formStudent,
                photo
        );

        ra.addFlashAttribute(
                "success",
                "Profile updated successfully"
        );

        return "redirect:/student/profile";
    }
}