package com.erp.controller;

import com.erp.entity.Course;
import com.erp.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseRepository courseRepository;

    public AdminCourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("course", new Course());
        return "admin/course-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Course course) {
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }
}
