package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.entity.Student;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public StudentAttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public List<Attendance> getMyAttendance(String username) {

        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return attendanceRepository
                .findByStudentIdOrderByDateDesc(student.getId());
    }
}
