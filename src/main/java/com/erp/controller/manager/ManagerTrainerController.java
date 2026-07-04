package com.erp.controller.manager;

import com.erp.enums.Role;
import com.erp.repository.EmployeeRepository;
import com.erp.repository.TrainerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/trainers")
public class ManagerTrainerController {

    @Autowired
    private TrainerRepository trainerRepository;

    @GetMapping
    public String listTrainers(Model model) {
        model.addAttribute("trainers", trainerRepository.findAll());
        return "manager/trainer-list";
    }
}

