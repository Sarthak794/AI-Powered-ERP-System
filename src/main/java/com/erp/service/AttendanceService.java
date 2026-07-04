package com.erp.service;

import com.erp.entity.*;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.erp.repository.BatchRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final StudentRepository studentRepo;
    private final BatchRepository batchRepo;

    public AttendanceService(
            AttendanceRepository attendanceRepo,
            StudentRepository studentRepo,
            BatchRepository batchRepo) {
        this.attendanceRepo = attendanceRepo;
        this.studentRepo = studentRepo;
        this.batchRepo = batchRepo;
    }

    @Transactional
    public void markAttendance(
            Long batchId,
            LocalDate date,
            List<Long> presentStudentIds) {

        Batch batch = batchRepo.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        List<Student> students =
                studentRepo.findByBatchId(batchId);

        for (Student student : students) {

            Attendance attendance = new Attendance();
            attendance.setDate(date);
            attendance.setStudent(student);
            attendance.setBatch(batch); // 🔥 THIS LINE FIXES EVERYTHING

            boolean present =
                    presentStudentIds.contains(student.getId());
            attendance.setPresent(present);

            attendanceRepo.save(attendance);
        }
    }
}
