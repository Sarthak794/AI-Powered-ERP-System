package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.repository.AttendanceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceExportService {

    private final AttendanceRepository attendanceRepo;

    public AttendanceExportService(AttendanceRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public ByteArrayInputStream exportExcel(
            Long batchId,
            LocalDate from,
            LocalDate to) throws Exception {

        List<Attendance> records =
                attendanceRepo.findByBatchIdAndDateBetween(
                        batchId, from, to
                );

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("Student Name");
        header.createCell(2).setCellValue("Status");

        int rowIdx = 1;
        for (Attendance a : records) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(a.getDate().toString());
            row.createCell(1)
               .setCellValue(a.getStudent()
                               .getUser()
                               .getFullName());
            row.createCell(2)
               .setCellValue(a.isPresent() ? "Present" : "Absent");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
