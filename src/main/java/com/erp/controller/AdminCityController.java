package com.erp.controller;

import com.erp.entity.City;
import com.erp.repository.CityRepository;
import com.erp.repository.StateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cities")
public class AdminCityController {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public AdminCityController(CityRepository cityRepository,
                               StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("city", new City());
        return "admin/city-list";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@RequestParam("state.id") Long stateId,
                       @ModelAttribute City city) {

        city.setState(stateRepository.findById(stateId).orElse(null));
        cityRepository.save(city);

        return "redirect:/admin/cities";
    }


    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return "redirect:/admin/cities";
    }
}
