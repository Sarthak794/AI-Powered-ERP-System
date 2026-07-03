package com.erp.dto;

public class BatchPerformanceDto {

    private int totalStudents;
    private double avgAttendance;
    private double avgExamScore;
    private String topPerformer;
    private long lowAttendanceCount;
    
    
	public BatchPerformanceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BatchPerformanceDto(int totalStudents, double avgAttendance, double avgExamScore, String topPerformer,
			long lowAttendanceCount) {
		super();
		this.totalStudents = totalStudents;
		this.avgAttendance = avgAttendance;
		this.avgExamScore = avgExamScore;
		this.topPerformer = topPerformer;
		this.lowAttendanceCount = lowAttendanceCount;
	}
	public int getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}
	public double getAvgAttendance() {
		return avgAttendance;
	}
	public void setAvgAttendance(double avgAttendance) {
		this.avgAttendance = avgAttendance;
	}
	public double getAvgExamScore() {
		return avgExamScore;
	}
	public void setAvgExamScore(double avgExamScore) {
		this.avgExamScore = avgExamScore;
	}
	public String getTopPerformer() {
		return topPerformer;
	}
	public void setTopPerformer(String topPerformer) {
		this.topPerformer = topPerformer;
	}
	public long getLowAttendanceCount() {
		return lowAttendanceCount;
	}
	public void setLowAttendanceCount(long lowAttendanceCount) {
		this.lowAttendanceCount = lowAttendanceCount;
	}

    // getters/setters
}