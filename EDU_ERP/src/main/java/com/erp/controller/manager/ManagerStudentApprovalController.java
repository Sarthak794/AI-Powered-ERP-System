package com.erp.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.entity.Student;
import com.erp.repository.StudentRepository;

@Controller
@RequestMapping("/manager/students")
public class ManagerStudentApprovalController {

    private final StudentRepository studentRepo;

    public ManagerStudentApprovalController(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("students", studentRepo.findByApprovedFalse());
        return "manager/student-approvals";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        Student student = studentRepo.findById(id).orElseThrow();
        student.setApproved(true);
        studentRepo.save(student);
        return "redirect:/manager/students/pending";
    }

}
