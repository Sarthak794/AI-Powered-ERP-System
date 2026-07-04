package com.erp.service;

import com.erp.entity.Document;
import com.erp.entity.StudentDocument;
import com.erp.enums.DocumentType;
import com.erp.repository.DocumentRepository;
import com.erp.repository.StudentDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentDocumentServiceImpl implements StudentDocumentService {

    private final DocumentRepository documentRepository;
    private final StudentDocumentRepository studentDocumentRepository;

    private final String UPLOAD_DIR =
            System.getProperty("user.dir")
            + File.separator
            + "uploads"
            + File.separator;

    // ✅ Manual constructor (replaces Lombok)
    public StudentDocumentServiceImpl(DocumentRepository documentRepository,
                                      StudentDocumentRepository studentDocumentRepository) {
        this.documentRepository = documentRepository;
        this.studentDocumentRepository = studentDocumentRepository;
    }

    @Override
    public void uploadDocument(Long studentId, MultipartFile file, DocumentType type, Long uploadedBy) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath =
                    UPLOAD_DIR
                    + studentId
                    + File.separator
                    + type.name()
                    + File.separator;

            File dir = new File(filePath);

            if (!dir.exists()) {

                boolean created = dir.mkdirs();

                System.out.println(
                        "Directory Created = "
                        + created
                );
            }

            File destination =
                    new File(dir, fileName);

            System.out.println(
                    destination.getAbsolutePath()
            );
            file.transferTo(destination);

            // ✅ Create Document manually (NO builder)
            Document document = new Document();
            document.setFileName(fileName);
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFilePath(destination.getAbsolutePath());
            document.setUploadedBy(uploadedBy);

            documentRepository.save(document);

            // Deactivate old version
            List<StudentDocument> existingDocs =
                    studentDocumentRepository.findByStudentIdAndIsActiveTrue(studentId);

            existingDocs.stream()
                    .filter(d -> d.getDocumentType() == type)
                    .forEach(d -> {
                        d.setIsActive(false);
                        studentDocumentRepository.save(d);
                    });

            // ✅ Create StudentDocument manually
            StudentDocument studentDocument = new StudentDocument();
            studentDocument.setStudentId(studentId);
            studentDocument.setDocument(document);
            studentDocument.setDocumentType(type);
            studentDocument.setIsActive(true);
            studentDocument.setVersion(1);

            studentDocumentRepository.save(studentDocument);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "File upload failed : " +
                    e.getMessage()
            );
        }    }

    @Override
    public List<StudentDocument> getStudentDocuments(Long studentId) {
        return studentDocumentRepository.findByStudentIdAndIsActiveTrue(studentId);
    }

    @Override
    public void deleteDocument(Long id) {
        StudentDocument sd = studentDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        sd.setIsActive(false);
        studentDocumentRepository.save(sd);
    }
    @Override
    public StudentDocument getDocumentById(Long id) {

        return studentDocumentRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Document not found"
                        )
                );
    }
}