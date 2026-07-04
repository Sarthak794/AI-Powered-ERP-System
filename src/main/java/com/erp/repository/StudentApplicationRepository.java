package com.erp.repository;

import com.erp.entity.ApplicationStatus;
import com.erp.entity.Student;
import com.erp.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentApplicationRepository
        extends JpaRepository<StudentApplication, Long> {

    /* =========================
        BASIC
    ========================= */

    List<StudentApplication> findByStudent(Student student);

    boolean existsByStudentIdAndPlacementDriveId(
            Long studentId,
            Long placementDriveId
    );

    List<StudentApplication> findByStatus(ApplicationStatus status);

    /* =========================
        KPI COUNTS (FIXED ENUM SAFE)
    ========================= */

    @Query("""
        SELECT COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
    """)
    long countSelected();

    @Query("""
        SELECT COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.REJECTED
    """)
    long countRejected();

    @Query("""
        SELECT COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.SHORTLISTED
    """)
    long countShortlisted();

    @Query("""
        SELECT COUNT(a)
        FROM StudentApplication a
    """)
    long countTotalApplications();

    /* =========================
        MONTHLY ANALYTICS
    ========================= */

    @Query("""
        SELECT FUNCTION('MONTH', a.appliedAt), COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
        GROUP BY FUNCTION('MONTH', a.appliedAt)
        ORDER BY FUNCTION('MONTH', a.appliedAt)
    """)
    List<Object[]> getMonthlyPlacements();

    /* =========================
        COMPANY WISE
    ========================= */

    @Query("""
        SELECT a.placementDrive.company.companyName, COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
        GROUP BY a.placementDrive.company.companyName
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> getCompanyWisePlacements();

    /* =========================
        BATCH WISE (NULL SAFE FIX)
    ========================= */

    @Query("""
        SELECT COALESCE(a.student.batch.name, 'UNKNOWN'), COUNT(a)
        FROM StudentApplication a
        WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
        GROUP BY a.student.batch.name
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> getBatchWisePlacements();

    /* =========================
        RESUME COUNT
    ========================= */

    @Query("""
        SELECT COUNT(a)
        FROM StudentApplication a
        WHERE a.student.resumePath IS NOT NULL
    """)
    long countResumeUploaded();
    
    @Query("""
    		SELECT
    		a.placementDrive.company.companyName,
    		COUNT(a)
    		FROM StudentApplication a
    		WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
    		GROUP BY a.placementDrive.company.companyName
    		ORDER BY COUNT(a) DESC
    		""")
    		List<Object[]> getTopCompany();
    		
    		@Query("""
    				SELECT
    				COALESCE(a.student.batch.name,'Unknown'),
    				COUNT(a)
    				FROM StudentApplication a
    				WHERE a.status = com.erp.entity.ApplicationStatus.SELECTED
    				GROUP BY a.student.batch.name
    				ORDER BY COUNT(a) DESC
    				""")
    				List<Object[]> getTopBatch();
}