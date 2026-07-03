package com.erp.controller.student;

import com.erp.service.StudentAttendanceService;
import com.erp.service.StudentService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentAttendanceController {

    private final StudentService studentService;

    public StudentAttendanceController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student/attendance")
    public String attendance(Model model) {

        model.addAttribute(
                "attendance",
                studentService.getMyAttendance()
        );

        model.addAttribute(
                "attendancePercentage",
                studentService.getAttendancePercentage()
        );

        model.addAttribute(
                "totalClasses",
                studentService.getTotalClasses()
        );

        model.addAttribute(
                "presentDays",
                studentService.getPresentDays()
        );

        model.addAttribute(
                "absentDays",
                studentService.getTotalClasses()
                - studentService.getPresentDays()
        );

        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "student/attendance");

        return "layout/student-layout";
    }
}
