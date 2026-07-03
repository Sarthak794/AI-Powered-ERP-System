package com.erp.controller.trainer;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.entity.Trainer;
import com.erp.service.TrainerProfileService;

@Controller
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    public TrainerProfileController(TrainerProfileService trainerProfileService) {
        this.trainerProfileService = trainerProfileService;
    }

    @GetMapping("/trainer/profile")
    public String trainerProfile(Authentication authentication,
                                 Model model) {

        String username = authentication.getName();

        Trainer trainer = trainerProfileService.getLoggedInTrainer(username);

        model.addAttribute("trainer", trainer);

        // Load inside common layout
        model.addAttribute("content", "trainer/profile");
        model.addAttribute("activePage", "profile");

        return "layout/trainer-layout";
    }

}