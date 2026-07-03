package com.erp.controller.trainer;

import com.erp.service.TrainerAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.entity.Assignment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/trainer/assignments")
public class TrainerAssignmentController {

    private final TrainerAssignmentService assignmentService;

    public TrainerAssignmentController(
            TrainerAssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String assignments(Model model) {

        model.addAttribute(
                "assignments",
                assignmentService.getMyAssignments());

        model.addAttribute(
                "content",
                "trainer/assignments");

        model.addAttribute(
                "activePage",
                "assignments");

        return "layout/trainer-layout";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "batches",
                assignmentService.getMyBatches());

        model.addAttribute(
                "content",
                "trainer/assignment-create");

        model.addAttribute(
                "activePage",
                "assignments");

        return "layout/trainer-layout";
    }
    
    @PostMapping("/create")
    public String saveAssignment(
            @ModelAttribute Assignment assignment,
            @RequestParam(required = false)
            MultipartFile file
    ) throws Exception {

        if(file != null && !file.isEmpty()) {

            String uploadDir =
                    "uploads/assignments/";

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

            assignment.setFilePath(
                    "/uploads/assignments/" + filename
            );
        }

        assignmentService.createAssignment(
                assignment
        );

        return "redirect:/trainer/assignments";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteAssignment(
            @PathVariable Long id
    ) {

        assignmentService.deleteAssignment(id);

        return "redirect:/trainer/assignments";
    }
    
    @GetMapping("/{id}/edit")
    public String editAssignment(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "assignment",
                assignmentService.getAssignment(id)
        );

        model.addAttribute(
                "batches",
                assignmentService.getMyBatches()
        );

        model.addAttribute(
                "content",
                "trainer/assignment-edit"
        );

        return "layout/trainer-layout";
    }
    
    @PostMapping("/{id}/edit")
    public String updateAssignment(
            @PathVariable Long id,
            @ModelAttribute Assignment form
    ) {

        Assignment a =
                assignmentService.getAssignment(id);

        a.setTitle(form.getTitle());
        a.setDescription(form.getDescription());
        a.setDueDate(form.getDueDate());

        assignmentService.createAssignment(a);

        return "redirect:/trainer/assignments";
    }
    

    @GetMapping("/{id}/submissions")
    public String submissions(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "assignment",
                assignmentService.getAssignment(id)
        );

        model.addAttribute(
                "submissions",
                assignmentService.getSubmissions(id)
        );

        model.addAttribute(
                "content",
                "trainer/assignment-submissions"
        );

        model.addAttribute(
                "activePage",
                "assignments"
        );

        return "layout/trainer-layout";
    }
    @GetMapping("/submission/{id}/review")
    public String reviewSubmission(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "submission",
                assignmentService.getSubmission(id)
        );

        model.addAttribute(
                "content",
                "trainer/submission-review"
        );

        model.addAttribute(
                "activePage",
                "assignments"
        );

        return "layout/trainer-layout";
    }
    @PostMapping("/submission/{id}/review")
    public String saveReview(
            @PathVariable Long id,
            @RequestParam Integer marksAwarded,
            @RequestParam String trainerRemarks
    ) {

        assignmentService.reviewSubmission(
                id,
                marksAwarded,
                trainerRemarks
        );

        return "redirect:/trainer/assignments";
    }
    
}