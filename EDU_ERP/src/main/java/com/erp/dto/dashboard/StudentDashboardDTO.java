package com.erp.dto.dashboard;

import com.erp.entity.Student;

public class StudentDashboardDTO {

	private Student student;

	// Attendance
	private double attendancePercentage;
	private long totalClasses;
	private long presentDays;

	// Fees
	private double pendingFees;

	// Exams
	private double averageScore;
	private int highestScore;
	private long upcomingExamCount;
	private String nextExamName;

	// Documents
	private long documentCount;

	// Profile
	private int profileCompletion;

	// Assignments
	private long assignedAssignments;
	private long submittedAssignments;
	private long pendingAssignments;

	// Placement
	private long appliedDrives;
	private long selectedDrives;
	private boolean offerReceived;
	public StudentDashboardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentDashboardDTO(Student student, double attendancePercentage, long totalClasses, long presentDays,
			double pendingFees, double averageScore, int highestScore, long upcomingExamCount, String nextExamName,
			long documentCount, int profileCompletion, long assignedAssignments, long submittedAssignments,
			long pendingAssignments, long appliedDrives, long selectedDrives, boolean offerReceived) {
		super();
		this.student = student;
		this.attendancePercentage = attendancePercentage;
		this.totalClasses = totalClasses;
		this.presentDays = presentDays;
		this.pendingFees = pendingFees;
		this.averageScore = averageScore;
		this.highestScore = highestScore;
		this.upcomingExamCount = upcomingExamCount;
		this.nextExamName = nextExamName;
		this.documentCount = documentCount;
		this.profileCompletion = profileCompletion;
		this.assignedAssignments = assignedAssignments;
		this.submittedAssignments = submittedAssignments;
		this.pendingAssignments = pendingAssignments;
		this.appliedDrives = appliedDrives;
		this.selectedDrives = selectedDrives;
		this.offerReceived = offerReceived;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public double getAttendancePercentage() {
		return attendancePercentage;
	}
	public void setAttendancePercentage(double attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}
	public long getTotalClasses() {
		return totalClasses;
	}
	public void setTotalClasses(long totalClasses) {
		this.totalClasses = totalClasses;
	}
	public long getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(long presentDays) {
		this.presentDays = presentDays;
	}
	public double getPendingFees() {
		return pendingFees;
	}
	public void setPendingFees(double pendingFees) {
		this.pendingFees = pendingFees;
	}
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	public int getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
	public long getUpcomingExamCount() {
		return upcomingExamCount;
	}
	public void setUpcomingExamCount(long upcomingExamCount) {
		this.upcomingExamCount = upcomingExamCount;
	}
	public String getNextExamName() {
		return nextExamName;
	}
	public void setNextExamName(String nextExamName) {
		this.nextExamName = nextExamName;
	}
	public long getDocumentCount() {
		return documentCount;
	}
	public void setDocumentCount(long documentCount) {
		this.documentCount = documentCount;
	}
	public int getProfileCompletion() {
		return profileCompletion;
	}
	public void setProfileCompletion(int profileCompletion) {
		this.profileCompletion = profileCompletion;
	}
	public long getAssignedAssignments() {
		return assignedAssignments;
	}
	public void setAssignedAssignments(long assignedAssignments) {
		this.assignedAssignments = assignedAssignments;
	}
	public long getSubmittedAssignments() {
		return submittedAssignments;
	}
	public void setSubmittedAssignments(long submittedAssignments) {
		this.submittedAssignments = submittedAssignments;
	}
	public long getPendingAssignments() {
		return pendingAssignments;
	}
	public void setPendingAssignments(long pendingAssignments) {
		this.pendingAssignments = pendingAssignments;
	}
	public long getAppliedDrives() {
		return appliedDrives;
	}
	public void setAppliedDrives(long appliedDrives) {
		this.appliedDrives = appliedDrives;
	}
	public long getSelectedDrives() {
		return selectedDrives;
	}
	public void setSelectedDrives(long selectedDrives) {
		this.selectedDrives = selectedDrives;
	}
	public boolean isOfferReceived() {
		return offerReceived;
	}
	public void setOfferReceived(boolean offerReceived) {
		this.offerReceived = offerReceived;
	}
}
