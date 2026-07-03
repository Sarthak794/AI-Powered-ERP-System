package com.erp.controller;

import com.erp.entity.Location;
import com.erp.repository.CityRepository;
import com.erp.repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/locations")
public class AdminLocationController {

    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;

    public AdminLocationController(LocationRepository locationRepository,
                                   CityRepository cityRepository) {
        this.locationRepository = locationRepository;
        this.cityRepository = cityRepository;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("location", new Location());
        return "admin/location-list";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@RequestParam("city.id") Long cityId,
                       @ModelAttribute Location location) {

        location.setCity(cityRepository.findById(cityId).orElse(null));
        locationRepository.save(location);

        return "redirect:/admin/locations";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        locationRepository.deleteById(id);
        return "redirect:/admin/locations";
    }
}
