package com.erp.service;

import com.erp.entity.StudentDocument;
import com.erp.enums.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentDocumentService {

    void uploadDocument(Long studentId, MultipartFile file, DocumentType type, Long uploadedBy);

    List<StudentDocument> getStudentDocuments(Long studentId);

    void deleteDocument(Long id);
    StudentDocument getDocumentById(Long id);
}