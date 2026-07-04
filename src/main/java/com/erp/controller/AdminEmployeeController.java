package com.erp.controller;

import com.erp.entity.User;
import com.erp.enums.Role;
import com.erp.repository.CenterRepository;
import com.erp.repository.EmployeeRepository;
import com.erp.service.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeRepository employeeRepository;
    private final CenterRepository centerRepository;
    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeRepository employeeRepository,
                                   CenterRepository centerRepository,
                                   EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.centerRepository = centerRepository;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("centers", centerRepository.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", new User());
        return "admin/employee-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user,
                       @RequestParam Role role,
                       @RequestParam Long centerId,
                       @RequestParam String designation,
                       @RequestParam Double salary) {

        employeeService.createEmployee(
                user.getUsername(),
                user.getUsername(),   // email
                user.getPassword(),   // raw password from form
                role,
                centerRepository.findById(centerId).orElseThrow(),
                designation,
                salary
        );

        return "redirect:/admin/employees";
    }
}
