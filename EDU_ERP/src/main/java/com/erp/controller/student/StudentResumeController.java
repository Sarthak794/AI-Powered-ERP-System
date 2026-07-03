package com.erp.controller.student;

import com.erp.entity.Student;
import com.erp.repository.StudentRepository;
import com.erp.service.ResumeAiService;
import com.erp.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/student")
public class StudentResumeController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final ResumeAiService resumeAiService;

    public StudentResumeController(
            StudentService studentService,
            StudentRepository studentRepository,
            ResumeAiService resumeAiService) {

        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.resumeAiService = resumeAiService;
    }

    @GetMapping("/resume")
    public String resumePage(Model model) {

        Student student = studentService.getCurrentStudent();

        model.addAttribute("student", student);

        // AI Analysis
        if (student.getResumePath() != null) {

            model.addAttribute(
                    "analysis",
                    resumeAiService.analyze(student)
            );

        }

        model.addAttribute("activePage", "resume");
        model.addAttribute("content", "student/resume");

        return "layout/student-layout";
    }

    @PostMapping("/resume/upload")
    public String uploadResume(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        Student student = studentService.getCurrentStudent();

        String uploadDir = "uploads/resumes/";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName =
                student.getId() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, fileName);

        Files.write(path, file.getBytes());

        student.setResumePath(fileName);

        studentRepository.save(student);

        return "redirect:/student/resume";
    }

}