package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminAttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AdminAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> getByBatch(Long batchId) {
        return attendanceRepository.findByBatchId(batchId);
    }

    public List<Attendance> getByBatchAndDate(Long batchId, LocalDate date) {
        return attendanceRepository.findByBatchIdAndDate(batchId, date);
    }
}
