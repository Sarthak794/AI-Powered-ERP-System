package com.erp.dto;

import java.util.List;

public class AdminDashboardDTO {

    // KPI
    private long totalUsers;
    private long totalAdmins;
    private long totalManagers;
    private long totalTrainers;
    private long totalStudents;
    private long totalAccountants;

    // Placement KPIs
    private long totalCompanies;
    private long totalDrives;
    private long totalApplications;
    private long selectedCount;
    private long shortlistedCount;
    private long rejectedCount;
    private double placementRate;

    // Analytics
    private String topCompany;
    private String topBatch;
    private double avgPackage;
    private double placementGrowth;

    // Charts
    private List<String> months;
    private List<Integer> monthlyPlacements;

    private List<String> companies;
    private List<Integer> companyPlacements;

    private List<String> batches;
    private List<Integer> batchSuccessRate;
	public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	public long getTotalAdmins() {
		return totalAdmins;
	}
	public void setTotalAdmins(long totalAdmins) {
		this.totalAdmins = totalAdmins;
	}
	public long getTotalManagers() {
		return totalManagers;
	}
	public void setTotalManagers(long totalManagers) {
		this.totalManagers = totalManagers;
	}
	public long getTotalTrainers() {
		return totalTrainers;
	}
	public void setTotalTrainers(long totalTrainers) {
		this.totalTrainers = totalTrainers;
	}
	public long getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(long totalStudents) {
		this.totalStudents = totalStudents;
	}
	public long getTotalAccountants() {
		return totalAccountants;
	}
	public void setTotalAccountants(long totalAccountants) {
		this.totalAccountants = totalAccountants;
	}
	public long getTotalCompanies() {
		return totalCompanies;
	}
	public void setTotalCompanies(long totalCompanies) {
		this.totalCompanies = totalCompanies;
	}
	public long getTotalDrives() {
		return totalDrives;
	}
	public void setTotalDrives(long totalDrives) {
		this.totalDrives = totalDrives;
	}
	public long getTotalApplications() {
		return totalApplications;
	}
	public void setTotalApplications(long totalApplications) {
		this.totalApplications = totalApplications;
	}
	public long getSelectedCount() {
		return selectedCount;
	}
	public void setSelectedCount(long selectedCount) {
		this.selectedCount = selectedCount;
	}
	public long getShortlistedCount() {
		return shortlistedCount;
	}
	public void setShortlistedCount(long shortlistedCount) {
		this.shortlistedCount = shortlistedCount;
	}
	public long getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(long rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public double getPlacementRate() {
		return placementRate;
	}
	public void setPlacementRate(double placementRate) {
		this.placementRate = placementRate;
	}
	public String getTopCompany() {
		return topCompany;
	}
	public void setTopCompany(String topCompany) {
		this.topCompany = topCompany;
	}
	public String getTopBatch() {
		return topBatch;
	}
	public void setTopBatch(String topBatch) {
		this.topBatch = topBatch;
	}
	public double getAvgPackage() {
		return avgPackage;
	}
	public void setAvgPackage(double avgPackage) {
		this.avgPackage = avgPackage;
	}
	public double getPlacementGrowth() {
		return placementGrowth;
	}
	public void setPlacementGrowth(double placementGrowth) {
		this.placementGrowth = placementGrowth;
	}
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	public List<Integer> getMonthlyPlacements() {
		return monthlyPlacements;
	}
	public void setMonthlyPlacements(List<Integer> monthlyPlacements) {
		this.monthlyPlacements = monthlyPlacements;
	}
	public List<String> getCompanies() {
		return companies;
	}
	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}
	public List<Integer> getCompanyPlacements() {
		return companyPlacements;
	}
	public void setCompanyPlacements(List<Integer> companyPlacements) {
		this.companyPlacements = companyPlacements;
	}
	public List<String> getBatches() {
		return batches;
	}
	public void setBatches(List<String> batches) {
		this.batches = batches;
	}
	public List<Integer> getBatchSuccessRate() {
		return batchSuccessRate;
	}
	public void setBatchSuccessRate(List<Integer> batchSuccessRate) {
		this.batchSuccessRate = batchSuccessRate;
	}

    // getters & setters
}