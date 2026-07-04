package com.erp.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResumeAnalysisResponse {

    // Main AI Metrics
    private int atsScore;
    private int resumeHealth;
    private int aiConfidence;

    // Career Analysis
    private String jobMatch;
    private String recommendedRole;
    private String resumeLevel;

    // Dashboard Flags
    private boolean recruiterReady;
    private int improvementPotential;

    // AI Results
    private List<String> detectedKeywords = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();
    private List<String> missingSections = new ArrayList<>();

    // Analytics
    private int keywordCoverage;

    // Section Wise Scores
    private Map<String, Integer> sectionScores =
            new LinkedHashMap<>();

    public ResumeAnalysisResponse() {
    }

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public int getResumeHealth() {
        return resumeHealth;
    }

    public void setResumeHealth(int resumeHealth) {
        this.resumeHealth = resumeHealth;
    }

    public int getAiConfidence() {
        return aiConfidence;
    }

    public void setAiConfidence(int aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public String getJobMatch() {
        return jobMatch;
    }

    public void setJobMatch(String jobMatch) {
        this.jobMatch = jobMatch;
    }

    public String getRecommendedRole() {
        return recommendedRole;
    }

    public void setRecommendedRole(String recommendedRole) {
        this.recommendedRole = recommendedRole;
    }

    public String getResumeLevel() {
        return resumeLevel;
    }

    public void setResumeLevel(String resumeLevel) {
        this.resumeLevel = resumeLevel;
    }

    public boolean isRecruiterReady() {
        return recruiterReady;
    }

    public void setRecruiterReady(boolean recruiterReady) {
        this.recruiterReady = recruiterReady;
    }

    public int getImprovementPotential() {
        return improvementPotential;
    }

    public void setImprovementPotential(int improvementPotential) {
        this.improvementPotential = improvementPotential;
    }

    public List<String> getDetectedKeywords() {
        return detectedKeywords;
    }

    public void setDetectedKeywords(List<String> detectedKeywords) {
        this.detectedKeywords = detectedKeywords;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getMissingSections() {
        return missingSections;
    }

    public void setMissingSections(List<String> missingSections) {
        this.missingSections = missingSections;
    }

    public int getKeywordCoverage() {
        return keywordCoverage;
    }

    public void setKeywordCoverage(int keywordCoverage) {
        this.keywordCoverage = keywordCoverage;
    }

    public Map<String, Integer> getSectionScores() {
        return sectionScores;
    }

    public void setSectionScores(Map<String, Integer> sectionScores) {
        this.sectionScores = sectionScores;
    }

}