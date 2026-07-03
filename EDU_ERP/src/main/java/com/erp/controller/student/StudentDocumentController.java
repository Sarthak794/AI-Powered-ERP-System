package com.erp.controller.student;

import com.erp.entity.StudentDocument;
import com.erp.enums.DocumentType;
import com.erp.service.StudentDocumentService;
import com.erp.service.StudentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

@Controller
@RequestMapping("/student/documents")
public class StudentDocumentController {
	private final StudentDocumentService studentDocumentService;
	private final StudentService studentService;

	public StudentDocumentController(
	        StudentDocumentService studentDocumentService,
	        StudentService studentService) {

	    this.studentDocumentService = studentDocumentService;
	    this.studentService = studentService;
	}

//    private final StudentDocumentService studentDocumentService;

//    public StudentDocumentController(StudentDocumentService studentDocumentService) {
//        this.studentDocumentService = studentDocumentService;
//    }

	@GetMapping
	public String viewDocuments(Model model) {

	    Long studentId =
	            studentService
	                    .getCurrentStudent()
	                    .getId();

	    model.addAttribute(
	            "documents",
	            studentDocumentService
	                    .getStudentDocuments(studentId));

	    model.addAttribute(
	            "types",
	            DocumentType.values());

	    model.addAttribute(
	            "studentId",
	            studentId);

	    model.addAttribute(
	            "activePage",
	            "documents");

	    model.addAttribute(
	            "content",
	            "student/documents");

	    return "layout/student-layout";
	}

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam Long studentId,
                                 @RequestParam MultipartFile file,
                                 @RequestParam DocumentType type) {

    	Long uploadedBy =
    	        studentService
    	                .getCurrentStudent()
    	                .getUser()
    	                .getId();
        studentDocumentService.uploadDocument(studentId, file, type, uploadedBy);

        return "redirect:/student/documents";
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id,
                                @RequestParam Long studentId) {

        studentDocumentService.deleteDocument(id);
        return "redirect:/student/documents";
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long id
    ) throws Exception {

        StudentDocument document =
                studentDocumentService
                        .getDocumentById(id);

        File file = new File(
                document.getDocument()
                        .getFilePath()
        );

        Resource resource =
                new UrlResource(
                        file.toURI()
                                .toURL()
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                document.getDocument()
                                        .getFileName() +
                                "\""
                )
                .body(resource);
    }
    
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewDocument(
            @PathVariable Long id
    ) throws Exception {

        StudentDocument document =
                studentDocumentService
                        .getDocumentById(id);

        File file = new File(
                document.getDocument()
                        .getFilePath()
        );

        Resource resource =
                new UrlResource(
                        file.toURI()
                                .toURL()
                );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(resource);
    }
}