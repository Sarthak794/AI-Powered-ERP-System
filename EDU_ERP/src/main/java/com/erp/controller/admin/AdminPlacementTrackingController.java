package com.erp.controller.admin;

import com.erp.service.StudentApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.erp.entity.Student;
import com.erp.repository.StudentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class AdminPlacementTrackingController {

    private final StudentApplicationService applicationService;
    private final StudentRepository studentRepository;

    public AdminPlacementTrackingController(
            StudentApplicationService applicationService,
            StudentRepository studentRepository) {

        this.applicationService = applicationService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/applications")
    public String applications(Model model) {

        model.addAttribute(
                "applications",
                applicationService.getTotalApplications()
        );
        model.addAttribute(
                "applications",
                applicationService.getAllApplications()
        );

        model.addAttribute("activePage",
                "applications");

        model.addAttribute("content",
                "admin/applications");

        return "layout/admin-layout";
    }

    @PostMapping("/applications/update")
    public String updateStatus(
            @RequestParam Long applicationId,
            @RequestParam String status,
            @RequestParam(required = false) String remarks) {

        applicationService.updateStatus(
                applicationId,
                status,
                remarks
        );

        return "redirect:/admin/applications";
    }
    @GetMapping("/resume/download/{studentId}")
    public ResponseEntity<Resource>
    downloadResume(
            @PathVariable Long studentId)
            throws Exception {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow();

        if (student.getResumePath() == null) {
            throw new RuntimeException(
                    "Resume not uploaded");
        }

        Path path =
                Paths.get("uploads/resumes/")
                        .resolve(
                                student.getResumePath());

        Resource resource =
                new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + student.getResumePath()
                                + "\"")
                .body(resource);
    }
}