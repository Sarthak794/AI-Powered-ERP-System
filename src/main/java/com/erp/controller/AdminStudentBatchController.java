package com.erp.controller;

import com.erp.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/student-batch")
public class AdminStudentBatchController {

    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;

    public AdminStudentBatchController(StudentRepository studentRepository,
                                       BatchRepository batchRepository) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
    }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("batches", batchRepository.findAll());
        return "admin/student-batch-map";
    }

    @PostMapping("/assign")
    public String assign(@RequestParam Long studentId,
                         @RequestParam Long batchId) {

        var student = studentRepository.findById(studentId).orElse(null);
        var batch = batchRepository.findById(batchId).orElse(null);

        student.setBatch(batch);
        studentRepository.save(student);

        return "redirect:/admin/student-batch";
    }
}
