package com.erp.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.repository.StudentRepository;
import com.erp.repository.TrainerRepository;
import com.erp.repository.FeeRepository;
import com.erp.repository.FeePaymentRepository;

@Controller
public class ManagerDashboardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @GetMapping("/manager/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalTrainers", trainerRepository.count());
        model.addAttribute("totalCollected", feePaymentRepository.getTotalCollected());
        model.addAttribute("totalPending", feeRepository.getTotalPending());

        return "manager/dashboard";
    }
}
