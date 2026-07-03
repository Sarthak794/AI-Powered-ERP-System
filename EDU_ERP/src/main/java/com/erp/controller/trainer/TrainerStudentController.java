package com.erp.controller.trainer;

import com.erp.service.TrainerStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainerStudentController {

    private final TrainerStudentService trainerStudentService;

    public TrainerStudentController(
            TrainerStudentService trainerStudentService
    ) {
        this.trainerStudentService = trainerStudentService;
    }

    @GetMapping("/trainer/students")
    public String trainerStudents(Model model) {

        model.addAttribute(
                "students",
                trainerStudentService.getMyStudents()
        );

        model.addAttribute("activePage", "students");
        model.addAttribute("content", "trainer/students");

        return "layout/trainer-layout";
    }
}