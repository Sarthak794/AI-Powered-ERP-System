package com.erp.service;

import com.erp.dto.ResumeAnalysisResponse;
import com.erp.entity.Student;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ResumeAiService {

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public ResumeAnalysisResponse analyze(Student student) {

        String resumeText = extractResumeText(student);

        ResumeAnalysisResponse response = new ResumeAnalysisResponse();

        int atsScore = calculateATS(resumeText);

        response.setAtsScore(atsScore);

        response.setResumeHealth(
                calculateResumeHealth(atsScore)
        );

        response.setAiConfidence(
                calculateAiConfidence(resumeText)
        );

        response.setJobMatch(
                calculateJobMatch(atsScore)
        );

        response.setResumeLevel(
                determineResumeLevel(atsScore)
        );

        response.setRecruiterReady(
                isRecruiterReady(atsScore)
        );

        response.setImprovementPotential(
                calculateImprovementPotential(atsScore)
        );

        List<String> keywords =
                detectKeywords(resumeText);

        response.setDetectedKeywords(keywords);

        response.setKeywordCoverage(
                keywords.size()
        );

        response.setMissingSections(
                findMissingSections(resumeText)
        );

        response.setSuggestions(
                generateSuggestions(
                        resumeText,
                        atsScore,
                        keywords
                )
        );

        response.setRecommendedRole(
                determineRecommendedRole(keywords)
        );

        response.setSectionScores(
                buildSectionScores(
                        resumeText,
                        keywords
                )
        );

        return response;
    }

    private String extractResumeText(Student student) {

        try {

            if (student == null)
                return "";

            if (student.getResumePath() == null)
                return "";

            File file =
                    new File(
                            UPLOAD_DIR +
                            student.getResumePath()
                    );

            if (!file.exists())
                return "";

            try (PDDocument document =
                         Loader.loadPDF(file)) {

                PDFTextStripper stripper =
                        new PDFTextStripper();

                return stripper.getText(document);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

            return "";

        }

    }

    private int calculateATS(String text) {

        text = text.toLowerCase();

        int score = 40;

        score += contains(text,
                "java") ? 8 : 0;

        score += contains(text,
                "spring") ? 8 : 0;

        score += contains(text,
                "spring boot") ? 8 : 0;

        score += contains(text,
                "hibernate") ? 5 : 0;

        score += contains(text,
                "mysql") ? 5 : 0;

        score += contains(text,
                "sql") ? 5 : 0;

        score += contains(text,
                "rest") ? 5 : 0;

        score += contains(text,
                "git") ? 4 : 0;

        score += contains(text,
                "maven") ? 4 : 0;

        score += contains(text,
                "project") ? 4 : 0;

        score += contains(text,
                "education") ? 3 : 0;

        score += contains(text,
                "skills") ? 3 : 0;

        return Math.min(score,100);

    }

    private boolean contains(
            String text,
            String keyword){

        return text.contains(keyword);

    }
    // ============================================================
    // RESUME HEALTH
    // ============================================================

    private int calculateResumeHealth(int atsScore) {

        if (atsScore >= 95)
            return 100;

        if (atsScore >= 90)
            return 96;

        if (atsScore >= 80)
            return 90;

        if (atsScore >= 70)
            return 82;

        if (atsScore >= 60)
            return 72;

        return 60;
    }

    // ============================================================
    // AI CONFIDENCE
    // ============================================================

    private int calculateAiConfidence(String text) {

        if (text == null || text.isBlank())
            return 0;

        int length = text.length();

        if (length >= 5000)
            return 99;

        if (length >= 3500)
            return 97;

        if (length >= 2500)
            return 95;

        if (length >= 1500)
            return 91;

        if (length >= 800)
            return 86;

        return 80;
    }

    // ============================================================
    // JOB MATCH
    // ============================================================

    private String calculateJobMatch(int atsScore) {

        if (atsScore >= 90)
            return "Excellent";

        if (atsScore >= 80)
            return "High";

        if (atsScore >= 65)
            return "Medium";

        return "Low";
    }

    // ============================================================
    // RESUME LEVEL
    // ============================================================

    private String determineResumeLevel(int atsScore) {

        if (atsScore >= 90)
            return "Professional";

        if (atsScore >= 80)
            return "Advanced";

        if (atsScore >= 65)
            return "Intermediate";

        return "Beginner";
    }

    // ============================================================
    // RECRUITER READY
    // ============================================================

    private boolean isRecruiterReady(int atsScore) {

        return atsScore >= 75;

    }

    // ============================================================
    // ATS IMPROVEMENT
    // ============================================================

    private int calculateImprovementPotential(int atsScore) {

        if (atsScore >= 95)
            return 0;

        return 100 - atsScore;

    }

    // ============================================================
    // RECOMMENDED ROLE
    // ============================================================

    private String determineRecommendedRole(List<String> keywords) {

        Set<String> skills =
                new HashSet<>(keywords);

        if (skills.contains("spring boot")
                && skills.contains("hibernate")
                && skills.contains("mysql")) {

            return "Java Full Stack Developer";
        }

        if (skills.contains("react")
                && skills.contains("javascript")) {

            return "Frontend Developer";
        }

        if (skills.contains("docker")
                && skills.contains("aws")) {

            return "Cloud / DevOps Engineer";
        }

        if (skills.contains("mysql")
                && skills.contains("sql")) {

            return "Backend Java Developer";
        }

        return "Software Developer";

    }
    // ============================================================
    // DETECT KEYWORDS
    // ============================================================

    private List<String> detectKeywords(String text) {

        text = text.toLowerCase();

        Set<String> keywords = new LinkedHashSet<>();

        String[] tech = {

                "java",
                "spring",
                "spring boot",
                "spring mvc",
                "hibernate",
                "jpa",
                "jdbc",
                "sql",
                "mysql",
                "postgresql",
                "mongodb",
                "rest",
                "rest api",
                "git",
                "github",
                "maven",
                "docker",
                "aws",
                "html",
                "css",
                "bootstrap",
                "javascript",
                "react",
                "angular",
                "postman",
                "junit",
                "mockito"

        };

        for (String skill : tech) {

            if (text.contains(skill.toLowerCase())) {

                keywords.add(skill);

            }

        }

        return new ArrayList<>(keywords);

    }

    // ============================================================
    // MISSING SECTIONS
    // ============================================================

    private List<String> findMissingSections(String text) {

        text = text.toLowerCase();

        List<String> missing = new ArrayList<>();

        checkSection(text, missing,
                "summary",
                "Professional Summary");

        checkSection(text, missing,
                "education",
                "Education");

        checkSection(text, missing,
                "skills",
                "Technical Skills");

        checkSection(text, missing,
                "project",
                "Projects");

        checkSection(text, missing,
                "experience",
                "Experience");

        checkSection(text, missing,
                "achievement",
                "Achievements");

        checkSection(text, missing,
                "certification",
                "Certifications");

        if (!text.contains("github"))
            missing.add("GitHub");

        if (!text.contains("linkedin"))
            missing.add("LinkedIn");

        return missing;

    }

    // ============================================================
    // AI SUGGESTIONS
    // ============================================================

    private List<String> generateSuggestions(
            String text,
            int atsScore,
            List<String> keywords) {

        text = text.toLowerCase();

        List<String> suggestions =
                new ArrayList<>();

        if (!keywords.contains("spring boot"))
            suggestions.add(
                    "Add Spring Boot projects."
            );

        if (!keywords.contains("hibernate"))
            suggestions.add(
                    "Mention Hibernate experience."
            );

        if (!keywords.contains("docker"))
            suggestions.add(
                    "Add Docker skills."
            );

        if (!keywords.contains("aws"))
            suggestions.add(
                    "Add AWS or Cloud experience."
            );

        if (!text.contains("github"))
            suggestions.add(
                    "Add GitHub profile link."
            );

        if (!text.contains("linkedin"))
            suggestions.add(
                    "Add LinkedIn profile."
            );

        if (!containsNumbers(text))
            suggestions.add(
                    "Use measurable achievements with numbers."
            );

        if (atsScore < 70)
            suggestions.add(
                    "Improve resume structure for better ATS score."
            );

        if (atsScore >= 90)
            suggestions.add(
                    "Excellent resume. Keep it updated."
            );

        return suggestions;

    }

    // ============================================================
    // SECTION SCORES
    // ============================================================

    private Map<String, Integer> buildSectionScores(
            String text,
            List<String> keywords) {

        text = text.toLowerCase();

        Map<String, Integer> scores =
                new LinkedHashMap<>();

        scores.put("Contact",
                10);

        scores.put("Education",
                text.contains("education") ? 10 : 0);

        scores.put("Skills",
                Math.min(
                        keywords.size(),
                        15
                ));

        scores.put("Projects",
                text.contains("project") ? 18 : 5);

        scores.put("Experience",
                text.contains("experience") ? 15 : 5);

        scores.put("Achievements",
                text.contains("achievement") ? 10 : 4);

        scores.put("Certifications",
                text.contains("certification") ? 10 : 0);

        return scores;

    }

    // ============================================================
    // HELPERS
    // ============================================================

    private boolean containsNumbers(String text) {

        return text.matches(".*\\d+.*");

    }

    private void checkSection(
            String text,
            List<String> missing,
            String keyword,
            String title) {

        if (!text.contains(keyword)) {

            missing.add(title);

        }

    }

}