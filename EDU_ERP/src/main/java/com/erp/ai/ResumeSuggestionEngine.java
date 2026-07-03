package com.erp.ai;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResumeSuggestionEngine {

    public List<String> generateSuggestions(
            ResumeSection resume,
            ResumeScoreCard score) {

        List<String> suggestions = new ArrayList<>();

        // Contact
        if (resume.getGithub().isBlank()) {
            suggestions.add("Add your GitHub profile to showcase your source code and projects.");
        }

        if (resume.getLinkedin().isBlank()) {
            suggestions.add("Include your LinkedIn profile to improve recruiter visibility.");
        }

        // Summary
        if (resume.getSummary().isBlank()) {
            suggestions.add("Add a professional summary of 3–4 lines highlighting your strengths.");
        }

        // Skills
        if (resume.getDetectedKeywords().size() < 10) {
            suggestions.add("Expand your technical skills with modern technologies like Spring Boot, REST APIs, Docker, Git, and AWS.");
        }

        // Projects
        if (resume.getProjects().isBlank()) {
            suggestions.add("Include at least 2 technical projects with technologies used and your contributions.");
        }

        // Experience
        if (resume.getExperience().isBlank()) {
            suggestions.add("Add internships, freelance work, or practical experience if available.");
        }

        // Achievements
        if (!containsNumbers(resume.getAchievements())) {
            suggestions.add("Use measurable achievements like 'Improved performance by 30%' or 'Developed 15 REST APIs'.");
        }

        // Certifications
        if (resume.getCertifications().isBlank()) {
            suggestions.add("Add relevant certifications such as AWS, Oracle Java, Spring Boot, or Google Cloud.");
        }

        // ATS
        if (score.getTotalScore() < 70) {
            suggestions.add("Your ATS score is below the recommended level. Focus on completing missing sections.");
        }

        if (score.getTotalScore() >= 70 && score.getTotalScore() < 85) {
            suggestions.add("Your resume is good, but adding quantified achievements and stronger project descriptions can further improve it.");
        }

        if (score.getTotalScore() >= 85) {
            suggestions.add("Excellent resume. Continue keeping your skills and projects up to date.");
        }

        return suggestions;
    }

    private boolean containsNumbers(String text) {

        return text.matches(".*\\d+.*");

    }

}