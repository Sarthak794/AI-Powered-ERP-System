package com.erp.service;

import com.erp.entity.StudentApplication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    public ByteArrayInputStream exportSelectedStudents(List<StudentApplication> list) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Selected Students");

            // Header Row
            Row header = sheet.createRow(0);

            String[] columns = {
                    "Student Name",
                    "Roll Number",
                    "Email",
                    "Phone",
                    "Company",
                    "Drive",
                    "Status"
            };

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;

            for (StudentApplication app : list) {

                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(app.getStudent().getFullName());
                row.createCell(1).setCellValue(app.getStudent().getRollNumber());
                row.createCell(2).setCellValue(app.getStudent().getEmail());
                row.createCell(3).setCellValue(app.getStudent().getPhone());

                row.createCell(4).setCellValue(
                        app.getPlacementDrive().getCompany().getCompanyName()
                );

                row.createCell(5).setCellValue(
                        app.getPlacementDrive().getDriveTitle()
                );

                row.createCell(6).setCellValue(app.getStatus().name());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error while exporting Excel file", e);
        }
    }
}