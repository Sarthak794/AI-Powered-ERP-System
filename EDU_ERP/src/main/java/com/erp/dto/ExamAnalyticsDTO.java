package com.erp.dto;

public class ExamAnalyticsDTO {

    private long totalAttempts;

    private double averageScore;

    private double highestScore;

    private double lowestScore;

    private long passedStudents;

    private long failedStudents;

    private double passPercentage;

	public ExamAnalyticsDTO(long totalAttempts, double averageScore, double highestScore, double lowestScore,
			long passedStudents, long failedStudents, double passPercentage) {
		super();
		this.totalAttempts = totalAttempts;
		this.averageScore = averageScore;
		this.highestScore = highestScore;
		this.lowestScore = lowestScore;
		this.passedStudents = passedStudents;
		this.failedStudents = failedStudents;
		this.passPercentage = passPercentage;
	}

	public ExamAnalyticsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(long totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public double getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(double highestScore) {
		this.highestScore = highestScore;
	}

	public double getLowestScore() {
		return lowestScore;
	}

	public void setLowestScore(double lowestScore) {
		this.lowestScore = lowestScore;
	}

	public long getPassedStudents() {
		return passedStudents;
	}

	public void setPassedStudents(long passedStudents) {
		this.passedStudents = passedStudents;
	}

	public long getFailedStudents() {
		return failedStudents;
	}

	public void setFailedStudents(long failedStudents) {
		this.failedStudents = failedStudents;
	}

	public double getPassPercentage() {
		return passPercentage;
	}

	public void setPassPercentage(double passPercentage) {
		this.passPercentage = passPercentage;
	}

    // getters setters
}