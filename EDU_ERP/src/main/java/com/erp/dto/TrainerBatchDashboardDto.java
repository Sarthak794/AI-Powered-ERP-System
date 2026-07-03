package com.erp.dto;

public class TrainerBatchDashboardDto {

    private Long batchId;
    private String batchName;

    private String centerName;
    private String qualificationName;
    private String specializationName;

    private long studentCount;
    private long attendanceCount;
    private long examCount;

    private double attendancePercentage;

	public TrainerBatchDashboardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerBatchDashboardDto(Long batchId, String batchName, String centerName, String qualificationName,
			String specializationName, int studentCount, long attendanceCount, long examCount,
			double attendancePercentage) {
		super();
		this.batchId = batchId;
		this.batchName = batchName;
		this.centerName = centerName;
		this.qualificationName = qualificationName;
		this.specializationName = specializationName;
		this.studentCount = studentCount;
		this.attendanceCount = attendanceCount;
		this.examCount = examCount;
		this.attendancePercentage = attendancePercentage;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getQualificationName() {
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}

	public String getSpecializationName() {
		return specializationName;
	}

	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}

	public long getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public long getAttendanceCount() {
		return attendanceCount;
	}

	public void setAttendanceCount(long attendanceCount) {
		this.attendanceCount = attendanceCount;
	}

	public long getExamCount() {
		return examCount;
	}

	public void setExamCount(long examCount) {
		this.examCount = examCount;
	}

	public double getAttendancePercentage() {
		return attendancePercentage;
	}

	public void setAttendancePercentage(double attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}

    // getters setters
}