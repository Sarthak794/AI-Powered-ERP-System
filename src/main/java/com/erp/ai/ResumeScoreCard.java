package com.erp.ai;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResumeScoreCard {

    private int contactScore;

    private int educationScore;

    private int skillScore;

    private int projectScore;

    private int experienceScore;

    private int achievementScore;

    private int certificationScore;

    private int keywordScore;

    private int formattingScore;

    private int totalScore;

    private Map<String,Integer> sectionScores =
            new LinkedHashMap<>();
    
    public int getContactScore() {
        return contactScore;
    }

    public void setContactScore(int contactScore) {
        this.contactScore = contactScore;
    }

    public int getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(int educationScore) {
        this.educationScore = educationScore;
    }

    public int getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(int skillScore) {
        this.skillScore = skillScore;
    }

    public int getProjectScore() {
        return projectScore;
    }

    public void setProjectScore(int projectScore) {
        this.projectScore = projectScore;
    }

    public int getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(int experienceScore) {
        this.experienceScore = experienceScore;
    }

    public int getAchievementScore() {
        return achievementScore;
    }

    public void setAchievementScore(int achievementScore) {
        this.achievementScore = achievementScore;
    }

    public int getCertificationScore() {
        return certificationScore;
    }

    public void setCertificationScore(int certificationScore) {
        this.certificationScore = certificationScore;
    }

    public int getKeywordScore() {
        return keywordScore;
    }

    public void setKeywordScore(int keywordScore) {
        this.keywordScore = keywordScore;
    }

    public int getFormattingScore() {
        return formattingScore;
    }

    public void setFormattingScore(int formattingScore) {
        this.formattingScore = formattingScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

	public Map<String, Integer> getSectionScores() {
		return sectionScores;
	}

	public void setSectionScores(Map<String, Integer> sectionScores) {
		this.sectionScores = sectionScores;
	}

}