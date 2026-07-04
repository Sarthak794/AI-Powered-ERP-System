package com.erp.controller.student;

import com.erp.entity.OfferLetter;
import com.erp.entity.Student;
import com.erp.repository.OfferLetterRepository;
import com.erp.service.StudentService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/student")
public class StudentOfferLetterController {

    private final OfferLetterRepository offerLetterRepository;
    private final StudentService studentService;

    public StudentOfferLetterController(
            OfferLetterRepository offerLetterRepository,
            StudentService studentService) {

        this.offerLetterRepository = offerLetterRepository;
        this.studentService = studentService;
    }

    @GetMapping("/offer-letters")
    public String offerLetters(Model model) {

        Student student = studentService.getCurrentStudent();

        OfferLetter offerLetter =
                offerLetterRepository
                        .findByApplicationStudentId(student.getId())
                        .orElse(null);

        model.addAttribute("offerLetter", offerLetter);
        model.addAttribute("activePage", "offer-letters");
        model.addAttribute("content", "student/offer-letters");

        return "layout/student-layout";
    }

    @GetMapping("/offer-letter/download/{id}")
    public ResponseEntity<Resource> downloadOfferLetter(
            @PathVariable Long id) throws Exception {

        Student currentStudent =
                studentService.getCurrentStudent();

        OfferLetter letter =
                offerLetterRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Offer letter not found"));

        // Security check
        if (!letter.getApplication()
                .getStudent()
                .getId()
                .equals(currentStudent.getId())) {

            throw new RuntimeException("Unauthorized access");
        }

        Path path = Paths.get("uploads/offer-letters")
                .resolve(letter.getFileName());

        Resource resource =
                new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                letter.getFileName() +
                                "\"")
                .body(resource);
    }
}