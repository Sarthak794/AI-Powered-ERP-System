package com.erp.controller.student;

import com.erp.service.StudentAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/student/assignments")
public class StudentAssignmentController {

    private final StudentAssignmentService assignmentService;

    public StudentAssignmentController(
            StudentAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String assignments(Model model) {

        model.addAttribute(
                "assignments",
                assignmentService.getMyAssignments());

        int assigned = assignmentService.getMyAssignments().size();

        // Replace these with real service methods if you have them
        int submitted = 0;
        int pending = assigned - submitted;

        model.addAttribute("assignedAssignments", assigned);
        model.addAttribute("submittedAssignments", submitted);
        model.addAttribute("pendingAssignments", pending);

        model.addAttribute("activePage", "assignments");
        model.addAttribute("content", "student/assignments");

        return "layout/student-layout";
    }
    
    @GetMapping("/{id}/submit")
    public String submitPage(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "assignment",
                assignmentService.getAssignment(id)
        );

        model.addAttribute(
                "content",
                "student/assignment-submit"
        );

        return "layout/student-layout";
    }
    
    @PostMapping("/{id}/submit")
    public String submitAssignment(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) throws Exception {

        String uploadDir =
                "uploads/submissions/";

        Files.createDirectories(
                Paths.get(uploadDir)
        );

        String filename =
                UUID.randomUUID()
                + "_"
                + file.getOriginalFilename();

        Path filepath =
                Paths.get(uploadDir, filename);

        Files.copy(
                file.getInputStream(),
                filepath,
                StandardCopyOption.REPLACE_EXISTING
        );

        assignmentService.submitAssignment(
                id,
                "/uploads/submissions/" + filename
        );

        return "redirect:/student/assignments";
    }
}