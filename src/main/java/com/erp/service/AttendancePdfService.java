package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.repository.AttendanceRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendancePdfService {

    private final AttendanceRepository attendanceRepo;

    public AttendancePdfService(AttendanceRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public ByteArrayInputStream exportPdf(
            Long batchId,
            LocalDate from,
            LocalDate to) {

        List<Attendance> records =
                attendanceRepo.findByBatchIdAndDateBetween(
                        batchId, from, to);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(
                new Paragraph("Attendance Report",
                    FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 16))
            );

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell("Date");
            table.addCell("Student");
            table.addCell("Status");

            for (Attendance a : records) {
                table.addCell(a.getDate().toString());
                table.addCell(
                    a.getStudent()
                     .getUser()
                     .getFullName());
                table.addCell(
                    a.isPresent() ? "Present" : "Absent");
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}