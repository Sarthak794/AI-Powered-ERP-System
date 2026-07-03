package com.erp.dto;

public class QuestionAnalyticsDTO {

    private String question;
    private long totalAttempts;
    private long correctAttempts;
    private double accuracy;

    public QuestionAnalyticsDTO() {
    }

    public QuestionAnalyticsDTO(
            String question,
            long totalAttempts,
            long correctAttempts,
            double accuracy
    ) {
        this.question = question;
        this.totalAttempts = totalAttempts;
        this.correctAttempts = correctAttempts;
        this.accuracy = accuracy;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public long getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(long correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}