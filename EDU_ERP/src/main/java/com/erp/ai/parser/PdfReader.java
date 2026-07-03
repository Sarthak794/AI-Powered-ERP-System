package com.erp.ai.parser;

import com.erp.entity.Student;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PdfReader {

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public String read(Student student) {

        try {

            if (student == null)
                return "";

            if (student.getResumePath() == null)
                return "";

            File file = new File(UPLOAD_DIR + student.getResumePath());

            if (!file.exists())
                return "";

            try (PDDocument document = Loader.loadPDF(file)) {

                PDFTextStripper stripper = new PDFTextStripper();

                return stripper.getText(document);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

            return "";

        }

    }

}