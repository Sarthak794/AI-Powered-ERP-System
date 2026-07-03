package com.erp.controller.student;

import com.erp.repository.StudentOnlinePaymentRepository;
import com.erp.repository.StudentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentPaymentHistoryController {

    private final StudentOnlinePaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public StudentPaymentHistoryController(
            StudentOnlinePaymentRepository paymentRepository,
            StudentRepository studentRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student/payments")
    public String paymentHistory(Model model) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        var student = studentRepository
                .findByUserUsername(username)
                .orElseThrow();

        model.addAttribute(
                "payments",
                paymentRepository.findByStudent(student)
        );

        model.addAttribute("activePage", "payments");
        model.addAttribute("content", "student/payments");

        return "layout/student-layout";
    }
}
