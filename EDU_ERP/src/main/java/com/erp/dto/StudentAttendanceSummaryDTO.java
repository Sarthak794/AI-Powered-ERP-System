package com.erp.dto;

public class StudentAttendanceSummaryDTO {

    private String studentName;
    private long total;
    private long present;
    private long absent;
    private double percentage;

    public StudentAttendanceSummaryDTO(
            String studentName,
            long total,
            long present) {

        this.studentName = studentName;
        this.total = total;
        this.present = present;
        this.absent = total - present;
        this.percentage =
                total == 0 ? 0 :
                (present * 100.0 / total);
    }

    public String getStudentName() { return studentName; }
    public long getTotal() { return total; }
    public long getPresent() { return present; }
    public long getAbsent() { return absent; }
    public double getPercentage() { return percentage; }
}
