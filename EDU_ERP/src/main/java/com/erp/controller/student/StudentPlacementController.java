package com.erp.controller.student;

import com.erp.entity.PlacementDrive;
import com.erp.entity.Student;
import com.erp.service.PlacementDriveService;
import com.erp.service.StudentApplicationService;
import com.erp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentPlacementController {

    private final PlacementDriveService placementDriveService;
    private final StudentApplicationService applicationService;
    private final StudentService studentService;

    public StudentPlacementController(
            PlacementDriveService placementDriveService,
            StudentApplicationService applicationService,
            StudentService studentService) {

        this.placementDriveService = placementDriveService;
        this.applicationService = applicationService;
        this.studentService = studentService;
    }

    @GetMapping("/placement-drives")
    public String placementDrives(Model model) {


        Student student =
                studentService.getCurrentStudent();


        model.addAttribute(
                "student",
                student
        );


        model.addAttribute(
                "drives",
                placementDriveService.getActiveDrives()
        );


        model.addAttribute(
                "applicationService",
                applicationService
        );


        model.addAttribute(
                "activePage",
                "placement-drives"
        );

        model.addAttribute(
                "content",
                "student/placement-drives"
        );


        return "layout/student-layout";
    }

    @PostMapping("/placement-drives/apply/{id}")
    public String apply(@PathVariable Long id) {

        Student student =
                studentService.getCurrentStudent();

        PlacementDrive drive =
                placementDriveService.getDriveById(id);

        applicationService.apply(student, drive);

        return "redirect:/student/my-applications";
    }

    @GetMapping("/my-applications")
    public String myApplications(Model model) {

        Student student =
                studentService.getCurrentStudent();

        model.addAttribute(
                "applications",
                applicationService
                        .getApplicationsByStudent(student)
        );

        model.addAttribute("activePage",
                "my-applications");

        model.addAttribute("content",
                "student/my-applications");

        return "layout/student-layout";
    }
}