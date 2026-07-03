package com.erp.repository;

import com.erp.entity.Attendance;
import com.erp.entity.Batch;
import com.erp.entity.Student;
import com.erp.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // ================= COUNTS =================

    long countByStudentId(Long studentId);

    long countByStudentIdAndPresentTrue(Long studentId);

    long countByBatchId(Long batchId);

    long countByBatchIdAndPresentTrue(Long batchId);

    long countByBatchIdAndDateAndPresentTrue(Long batchId, LocalDate date);

    long countByBatchIdAndDateAndPresentFalse(Long batchId, LocalDate date);

    long countByStudent(Student student);

    long countByStudentAndPresentTrue(Student student);

    // ================= EXISTS =================

    boolean existsByStudentAndDate(Student student, LocalDate date);

    boolean existsByStudentIdAndDate(Long studentId, LocalDate date);

    boolean existsByBatchIdAndDate(Long batchId, LocalDate date);

    // ================= FIND BY STUDENT =================

    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);

    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByStudentIdOrderByDateDesc(Long studentId);

    // ================= FIND BY BATCH =================

    List<Attendance> findByBatchId(Long batchId);

    List<Attendance> findByBatchIdAndDate(Long batchId, LocalDate date);

    List<Attendance> findByBatchAndDate(Batch batch, LocalDate date);

    List<Attendance> findByBatchTrainerAndDate(
            Trainer trainer,
            LocalDate date
    );

    List<Attendance> findByBatchIdAndDateBetween(
            Long batchId,
            LocalDate from,
            LocalDate to
    );

    // ================= DATE RANGE =================

    @Query("""
        SELECT a
        FROM Attendance a
        WHERE a.batch.id = :batchId
        AND a.date BETWEEN :from AND :to
    """)
    List<Attendance> findByBatchAndDateRange(
            @Param("batchId") Long batchId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // =====================================================
    // STUDENT ATTENDANCE %
    // =====================================================

    @Query("""
        SELECT
            COALESCE(
                SUM(
                    CASE WHEN a.present = true THEN 1 ELSE 0 END
                ) * 100.0 / COUNT(a),
                0
            )
        FROM Attendance a
        WHERE a.student.id = :studentId
    """)
    Double getAttendancePercent(
            @Param("studentId") Long studentId
    );

    // =====================================================
    // BATCH AVERAGE ATTENDANCE %
    // =====================================================

    @Query("""
        SELECT
            COALESCE(
                SUM(
                    CASE WHEN a.present = true THEN 1 ELSE 0 END
                ) * 100.0 / COUNT(a),
                0
            )
        FROM Attendance a
        WHERE a.batch.id = :batchId
    """)
    Double avgAttendanceByBatch(
            @Param("batchId") Long batchId
    );

    // =====================================================
    // TRAINER AVERAGE ATTENDANCE %
    // =====================================================

    @Query("""
        SELECT
            COALESCE(
                SUM(
                    CASE WHEN a.present = true THEN 1 ELSE 0 END
                ) * 100.0 / COUNT(a),
                0
            )
        FROM Attendance a
        WHERE a.batch.trainer.id = :trainerId
    """)
    Double avgAttendanceByTrainer(
            @Param("trainerId") Long trainerId
    );

    // =====================================================
    // LOW ATTENDANCE STUDENTS
    // =====================================================

    @Query("""
        SELECT COUNT(s)
        FROM Student s
        WHERE s.batch.id = :batchId
        AND (
            (
                SELECT COUNT(a1)
                FROM Attendance a1
                WHERE a1.student = s
                AND a1.present = true
            ) * 100.0
            /
            (
                SELECT COUNT(a2)
                FROM Attendance a2
                WHERE a2.student = s
            )
        ) < :threshold
    """)
    Long countLowAttendance(
            @Param("batchId") Long batchId,
            @Param("threshold") double threshold
    );
 // =====================================================
 // UPDATE ATTENDANCE
 // =====================================================

 @Query("""
     SELECT a
     FROM Attendance a
     WHERE a.batch.id = :batchId
     AND a.date = :date
     ORDER BY a.student.fullName
 """)
 List<Attendance> findAttendanceForEditing(
         @Param("batchId") Long batchId,
         @Param("date") LocalDate date
 );
}