package com.erp.controller;

import com.erp.entity.State;
import com.erp.repository.StateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/states")
public class AdminStateController {

    private final StateRepository stateRepository;

    public AdminStateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("state", new State());
        return "admin/state-list";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute State state) {
        stateRepository.save(state);
        return "redirect:/admin/states";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        stateRepository.deleteById(id);
        return "redirect:/admin/states";
    }
}
