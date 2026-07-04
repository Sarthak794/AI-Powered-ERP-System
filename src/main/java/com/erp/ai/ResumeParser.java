package com.erp.ai;

import com.erp.entity.Student;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ResumeParser {

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public ResumeSection parse(Student student) {

        ResumeSection resume = new ResumeSection();

        try {

            if (student.getResumePath() == null)
                return resume;

            File file = new File(UPLOAD_DIR + student.getResumePath());

            if (!file.exists())
                return resume;

            PDDocument document = Loader.loadPDF(file);

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            document.close();

            resume.setFullText(text);

            extractContact(resume, text);

            extractLinks(resume, text);

            extractSections(resume, text);

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return resume;

    }

    private void extractContact(ResumeSection resume, String text) {

        Pattern emailPattern =
                Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");

        Matcher email = emailPattern.matcher(text);

        if (email.find()) {

            resume.setContact(email.group());

        }

    }

    private void extractLinks(ResumeSection resume, String text) {

        String lower = text.toLowerCase();

        if (lower.contains("github")) {

            resume.setGithub("Found");

        }

        if (lower.contains("linkedin")) {

            resume.setLinkedin("Found");

        }

    }

    private void extractSections(ResumeSection resume, String text) {

        String lower = text.toLowerCase();

        resume.setSummary(extractBlock(lower,
                "summary",
                "education"));

        resume.setEducation(extractBlock(lower,
                "education",
                "skills"));

        resume.setSkills(extractBlock(lower,
                "skills",
                "projects"));

        resume.setProjects(extractBlock(lower,
                "projects",
                "experience"));

        resume.setExperience(extractBlock(lower,
                "experience",
                "achievements"));

        resume.setAchievements(extractBlock(lower,
                "achievements",
                "certifications"));

        resume.setCertifications(extractBlock(lower,
                "certifications",
                "languages"));

        resume.setLanguages(extractBlock(lower,
                "languages",
                "hobbies"));

        resume.setHobbies(extractBlock(lower,
                "hobbies",
                ""));

    }

    private String extractBlock(String text,
                                String start,
                                String end) {

        int s = text.indexOf(start);

        if (s == -1)
            return "";

        if (end.isBlank()) {

            return text.substring(s);

        }

        int e = text.indexOf(end, s);

        if (e == -1)
            return text.substring(s);

        return text.substring(s, e);

    }

}