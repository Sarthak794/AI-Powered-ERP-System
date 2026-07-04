package com.erp.dto;

public class TrainerKpiDto {

    private long totalBatches;
    private long totalStudents;
    private long totalExams;
    private double avgAttendance;
    
    
    
	public TrainerKpiDto(long totalBatches, long totalStudents, long totalExams, double avgAttendance) {
		super();
		this.totalBatches = totalBatches;
		this.totalStudents = totalStudents;
		this.totalExams = totalExams;
		this.avgAttendance = avgAttendance;
	}
	public TrainerKpiDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getTotalBatches() {
		return totalBatches;
	}
	public void setTotalBatches(long totalBatches) {
		this.totalBatches = totalBatches;
	}
	public long getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(long totalStudents) {
		this.totalStudents = totalStudents;
	}
	public long getTotalExams() {
		return totalExams;
	}
	public void setTotalExams(long totalExams) {
		this.totalExams = totalExams;
	}
	public double getAvgAttendance() {
		return avgAttendance;
	}
	public void setAvgAttendance(double avgAttendance) {
		this.avgAttendance = avgAttendance;
	}

    // getters/setters
}