package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.entity.Batch;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.BatchRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;
import com.erp.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainerAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public TrainerAttendanceService(
            AttendanceRepository attendanceRepository,
            BatchRepository batchRepository,
            StudentRepository studentRepository,
            UserRepository userRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.batchRepository = batchRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    // =====================================================
    // Logged-in Trainer
    // =====================================================

    private User getCurrentTrainer() {

        String username = SecurityUtil.getCurrentUsername();

        if (username == null) {
            throw new IllegalStateException("No logged-in user");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException("Trainer not found"));
    }

    // =====================================================
    // Trainer Batches
    // =====================================================

    public List<Batch> getMyBatches() {

        User trainer = getCurrentTrainer();

        return batchRepository.findByTrainerUser(trainer);
    }

    // =====================================================
    // Students of Selected Batch
    // =====================================================

    public List<Student> getStudentsByBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Batch not found"));

        // Security Check
        if (!batch.getTrainer()
                .getUser()
                .getId()
                .equals(getCurrentTrainer().getId())) {

            throw new SecurityException("Unauthorized batch access");
        }

        return studentRepository.findByBatchId(batchId);
    }

    // =====================================================
    // Single Student Attendance
    // =====================================================

    public void markAttendance(
            Long studentId,
            Long batchId,
            LocalDate date,
            boolean present
    ) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Batch not found"));

        // Security Check
        if (!batch.getTrainer()
                .getUser()
                .getId()
                .equals(getCurrentTrainer().getId())) {

            throw new SecurityException("Unauthorized batch access");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        Attendance attendance = attendanceRepository
                .findByStudentAndDate(student, date)
                .orElse(new Attendance());

        attendance.setStudent(student);
        attendance.setBatch(batch);
        attendance.setDate(date);
        attendance.setPresent(present);

        attendanceRepository.save(attendance);
    }
    // =====================================================
    // Bulk Attendance (Save + Update)
    // =====================================================

    public void markBulkAttendance(
            Long batchId,
            LocalDate date,
            List<Long> presentStudentIds
    ) {

        // Prevent future attendance
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalStateException(
                    "Future attendance cannot be marked."
            );
        }

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Batch not found"));

        // Security Check
        if (!batch.getTrainer()
                .getUser()
                .getId()
                .equals(getCurrentTrainer().getId())) {

            throw new SecurityException("Unauthorized batch access");
        }

        boolean attendanceAlreadyMarked =
                attendanceRepository.existsByBatchIdAndDate(batchId, date);

        List<Student> students =
                studentRepository.findByBatchId(batchId);

        for (Student student : students) {

            boolean present = presentStudentIds != null
                    && presentStudentIds.contains(student.getId());

            Attendance attendance;

            if (attendanceAlreadyMarked) {

                attendance = attendanceRepository
                        .findByStudentAndDate(student, date)
                        .orElse(new Attendance());

            } else {

                attendance = new Attendance();

            }

            attendance.setStudent(student);
            attendance.setBatch(batch);
            attendance.setDate(date);
            attendance.setPresent(present);

            attendanceRepository.save(attendance);
        }
    }

    // =====================================================
    // Attendance Already Marked?
    // =====================================================

    public boolean isAttendanceMarked(
            Long batchId,
            LocalDate date
    ) {

        return attendanceRepository
                .existsByBatchIdAndDate(batchId, date);
    }

    // =====================================================
    // Attendance Map (For Checkbox Auto Selection)
    // =====================================================

    public Map<Long, Boolean> getAttendanceMap(
            Long batchId,
            LocalDate date
    ) {

        List<Attendance> attendanceList =
                attendanceRepository.findByBatchIdAndDate(batchId, date);

        Map<Long, Boolean> attendanceMap = new HashMap<>();

        for (Attendance attendance : attendanceList) {

            attendanceMap.put(
                    attendance.getStudent().getId(),
                    attendance.isPresent()
            );
        }

        return attendanceMap;
    }

    // =====================================================
    // Attendance Summary
    // =====================================================

    public Map<String, Long> getAttendanceSummary(
            Long batchId,
            LocalDate date
    ) {

        Map<String, Long> summary = new HashMap<>();

        summary.put(
                "present",
                attendanceRepository
                        .countByBatchIdAndDateAndPresentTrue(batchId, date)
        );

        summary.put(
                "absent",
                attendanceRepository
                        .countByBatchIdAndDateAndPresentFalse(batchId, date)
        );

        return summary;
    }

    // =====================================================
    // Attendance Records By Batch
    // =====================================================

    public List<Attendance> getAttendanceByBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Batch not found"));

        if (!batch.getTrainer()
                .getUser()
                .getId()
                .equals(getCurrentTrainer().getId())) {

            throw new SecurityException("Unauthorized batch access");
        }

        return attendanceRepository.findByBatchId(batchId);
    }

    // =====================================================
    // Attendance Records By Batch & Date
    // =====================================================

    public List<Attendance> getAttendanceByBatchAndDate(
            Long batchId,
            LocalDate date
    ) {

        return attendanceRepository
                .findByBatchIdAndDate(batchId, date);
    }
    // =====================================================
    // Batch Attendance Percentage
    // =====================================================

    public Map<String, Double> getAttendancePercentagePerBatch() {

        User trainer = getCurrentTrainer();

        List<Batch> batches = batchRepository.findByTrainerUser(trainer);

        Map<String, Double> result = new LinkedHashMap<>();

        for (Batch batch : batches) {

            long total = attendanceRepository.countByBatchId(batch.getId());

            long present = attendanceRepository
                    .countByBatchIdAndPresentTrue(batch.getId());

            double percentage = total == 0
                    ? 0
                    : (present * 100.0) / total;

            result.put(
                    batch.getName(),
                    Math.round(percentage * 100.0) / 100.0
            );
        }

        return result;
    }

}