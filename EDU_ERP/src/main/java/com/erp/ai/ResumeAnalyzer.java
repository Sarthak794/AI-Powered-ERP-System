package com.erp.ai;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResumeAnalyzer {

    public ResumeScoreCard analyze(ResumeSection resume) {

        ResumeScoreCard score = new ResumeScoreCard();

        score.setContactScore(scoreContact(resume));

        score.setEducationScore(scoreEducation(resume));

        score.setSkillScore(scoreSkills(resume));

        score.setProjectScore(scoreProjects(resume));

        score.setExperienceScore(scoreExperience(resume));

        score.setAchievementScore(scoreAchievements(resume));

        score.setCertificationScore(scoreCertifications(resume));

        score.setKeywordScore(scoreKeywords(resume));

        score.setFormattingScore(scoreFormatting(resume));

        int total =
                score.getContactScore()
                + score.getEducationScore()
                + score.getSkillScore()
                + score.getProjectScore()
                + score.getExperienceScore()
                + score.getAchievementScore()
                + score.getCertificationScore()
                + score.getKeywordScore()
                + score.getFormattingScore();

        score.setTotalScore(total);

        return score;
    }

    private int scoreContact(ResumeSection resume) {

        int score = 0;

        if (!resume.getContact().isBlank())
            score += 5;

        if (!resume.getGithub().isBlank())
            score += 2;

        if (!resume.getLinkedin().isBlank())
            score += 3;

        return Math.min(score, 10);
    }

    private int scoreEducation(ResumeSection resume) {

        return resume.getEducation().isBlank() ? 0 : 10;
    }

    private int scoreSkills(ResumeSection resume) {

        if (resume.getSkills().isBlank())
            return 0;

        int count = resume.getDetectedKeywords().size();

        return Math.min(15, count);
    }

    private int scoreProjects(ResumeSection resume) {

        if (resume.getProjects().isBlank())
            return 0;

        int score = 10;

        String project = resume.getProjects().toLowerCase();

        if (project.contains("spring"))
            score += 3;

        if (project.contains("java"))
            score += 2;

        if (project.contains("mysql"))
            score += 2;

        if (project.contains("rest"))
            score += 3;

        return Math.min(score,20);
    }

    private int scoreExperience(ResumeSection resume) {

        if (resume.getExperience().isBlank())
            return 0;

        int score = 10;

        String exp = resume.getExperience().toLowerCase();

        if (exp.contains("intern"))
            score += 2;

        if (containsNumbers(exp))
            score += 3;

        return Math.min(score,15);
    }

    private int scoreAchievements(ResumeSection resume) {

        if (resume.getAchievements().isBlank())
            return 0;

        if (containsNumbers(resume.getAchievements()))
            return 10;

        return 6;
    }

    private int scoreCertifications(ResumeSection resume) {

        return resume.getCertifications().isBlank()
                ? 0
                : 10;
    }

    private int scoreKeywords(ResumeSection resume) {

        int count = resume.getDetectedKeywords().size();

        return Math.min(count,5);
    }

    private int scoreFormatting(ResumeSection resume) {

        int score = 5;

        if (resume.getFullText().length() < 500)
            score -= 2;

        if (resume.getFullText().length() > 7000)
            score -= 1;

        return Math.max(score,2);
    }

    private boolean containsNumbers(String text) {

        return text.matches(".*\\d+.*");
    }

    public List<String> findMissingSections(ResumeSection resume) {

        List<String> missing = new ArrayList<>();

        if (resume.getSummary().isBlank())
            missing.add("Professional Summary");

        if (resume.getEducation().isBlank())
            missing.add("Education");

        if (resume.getSkills().isBlank())
            missing.add("Skills");

        if (resume.getProjects().isBlank())
            missing.add("Projects");

        if (resume.getExperience().isBlank())
            missing.add("Experience");

        if (resume.getAchievements().isBlank())
            missing.add("Achievements");

        if (resume.getCertifications().isBlank())
            missing.add("Certifications");

        if (resume.getGithub().isBlank())
            missing.add("GitHub Profile");

        if (resume.getLinkedin().isBlank())
            missing.add("LinkedIn Profile");

        return missing;
    }

}