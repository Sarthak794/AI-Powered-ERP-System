package com.erp.controller.admin;

import com.erp.entity.StudentApplication;
import com.erp.service.OfferLetterPdfService;
import com.erp.service.OfferLetterService;
import com.erp.service.StudentApplicationService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.file.*;

@Controller
@RequestMapping("/admin")
public class AdminOfferLetterController {

    private final StudentApplicationService applicationService;
    private final OfferLetterService offerLetterService;
    private final OfferLetterPdfService offerLetterPdfService;

    public AdminOfferLetterController(
            StudentApplicationService applicationService,
            OfferLetterService offerLetterService,
            OfferLetterPdfService offerLetterPdfService) {

        this.applicationService = applicationService;
        this.offerLetterService = offerLetterService;
        this.offerLetterPdfService = offerLetterPdfService;
    }

    @PostMapping("/offer-letter/upload")
    public String uploadOfferLetter(
            @RequestParam Long applicationId,
            @RequestParam MultipartFile file) throws Exception {

        StudentApplication application =
                applicationService.getById(applicationId);

        String uploadDir = "uploads/offer-letters/";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = applicationId + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, fileName);

        Files.write(path, file.getBytes());

        offerLetterService.save(application, fileName);

        return "redirect:/admin/applications";
    }

    @GetMapping("/offer-letter/generate/{applicationId}")
    public ResponseEntity<InputStreamResource> generateOfferLetter(
            @PathVariable Long applicationId) {

        StudentApplication app =
                applicationService.getById(applicationId);

        ByteArrayInputStream stream =
                offerLetterPdfService.generateOfferLetter(app);

        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Content-Disposition",
                "attachment; filename=offer_letter_" + applicationId + ".pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }
}