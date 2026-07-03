package com.erp.controller.admin;

import com.erp.service.ExcelExportService;
import com.erp.service.StudentApplicationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;

@Controller
public class AdminExportController {

    private final StudentApplicationService applicationService;
    private final ExcelExportService excelService;

    public AdminExportController(
            StudentApplicationService applicationService,
            ExcelExportService excelService) {

        this.applicationService = applicationService;
        this.excelService = excelService;
    }

    @GetMapping("/admin/export/selected-students")
    public ResponseEntity<InputStreamResource> exportSelectedStudents() {

        ByteArrayInputStream stream =
                excelService.exportSelectedStudents(
                        applicationService.getSelectedApplications()
                );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=selected_students.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}