package com.erp.dto;

public class BatchStudentDto {

    private String rollNumber;
    private String name;
    private String email;
    private String phone;

    private double attendancePercentage;
    private int examScore;
	public BatchStudentDto(String rollNumber, String name, String email, String phone, double attendancePercentage,
			int examScore) {
		super();
		this.rollNumber = rollNumber;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.attendancePercentage = attendancePercentage;
		this.examScore = examScore;
	}
	public BatchStudentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getAttendancePercentage() {
		return attendancePercentage;
	}
	public void setAttendancePercentage(double attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}
	public int getExamScore() {
		return examScore;
	}
	public void setExamScore(int examScore) {
		this.examScore = examScore;
	}

    // getters/setters
}