package com.erp.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.service.StudentAssignmentService;
import com.erp.service.StudentDashboardService;
import com.erp.service.StudentService;
import com.erp.repository.AssignmentSubmissionRepository;
import com.erp.repository.StudentApplicationRepository;
import com.erp.repository.OfferLetterRepository;
import com.erp.repository.ExamRepository;
import com.erp.entity.ApplicationStatus;

@Controller
public class StudentDashboardController {

	private final StudentService studentService;
	private final StudentDashboardService dashboardService;
	private final StudentAssignmentService assignmentService;
	private final AssignmentSubmissionRepository submissionRepository;
	private final StudentApplicationRepository applicationRepository;
	private final OfferLetterRepository offerLetterRepository;
	private final ExamRepository examRepository;

	public StudentDashboardController(
	        StudentService studentService,
	        StudentDashboardService dashboardService,
	        StudentAssignmentService assignmentService,
	        AssignmentSubmissionRepository submissionRepository,
	        StudentApplicationRepository applicationRepository,
	        OfferLetterRepository offerLetterRepository,
	        ExamRepository examRepository
	        
	) {
	    this.studentService = studentService;
	    this.dashboardService = dashboardService;
	    this.assignmentService = assignmentService;
	    this.submissionRepository = submissionRepository;
	    this.applicationRepository = applicationRepository;
	    this.offerLetterRepository = offerLetterRepository;
	    this.examRepository = examRepository;
	}

	@GetMapping("/student/dashboard")
	public String dashboard(Model model) {

	    var student =
	            studentService.getCurrentStudent();
	    
	    long assignedAssignments =
	            assignmentService.getMyAssignments().size();

	    long submittedAssignments =
	            submissionRepository.countByStudentId(
	                    student.getId()
	            );

	    long pendingAssignments =
	            Math.max(
	                    0,
	                    assignedAssignments - submittedAssignments
	            );
	    var applications =
	            applicationRepository.findByStudent(student);

	    long appliedDrives =
	            applications.size();

	    long selectedDrives =
	            applications.stream()
	                    .filter(a ->
	                            a.getStatus() == ApplicationStatus.SELECTED
	                    )
	                    .count();

	    boolean offerReceived =
	            offerLetterRepository
	                    .findByApplicationStudentId(
	                            student.getId()
	                    )
	                    .isPresent();
	    
	    var upcomingExams =
	            examRepository
	                    .findByBatchAndActiveTrueAndStartDateAfter(
	                            student.getBatch(),
	                            java.time.LocalDate.now()
	                    );
	    
	    String nextExamName = "-";

	    if (!upcomingExams.isEmpty()) {

	        nextExamName =
	                upcomingExams.stream()
	                        .sorted(
	                                java.util.Comparator.comparing(
	                                        e -> e.getStartDate()
	                                )
	                        )
	                        .findFirst()
	                        .map(e -> e.getTitle())
	                        .orElse("-");
	    }
	    model.addAttribute(
	            "attendancePercentage",
	            studentService.getAttendancePercentage());

	    model.addAttribute(
	            "totalClasses",
	            studentService.getTotalClasses());

	    model.addAttribute(
	            "presentDays",
	            studentService.getPresentDays());

	    model.addAttribute(
	            "student",
	            student);

	    model.addAttribute(
	            "pendingFees",
	            dashboardService.getPendingFees(student));

	    model.addAttribute(
	            "averageScore",
	            dashboardService.getAverageScore(student));

	    model.addAttribute(
	            "highestScore",
	            dashboardService.getHighestScore(student));

	    model.addAttribute(
	            "documentCount",
	            dashboardService.getDocumentCount(student));
	    
	    model.addAttribute(
	            "profileCompletion",
	            dashboardService.getProfileCompletionPercentage(student)
	    );

	    model.addAttribute(
	            "activePage",
	            "dashboard");

	    model.addAttribute(
	            "content",
	            "student/dashboard");
	    model.addAttribute(
	            "assignmentCount",
	            assignmentService.getMyAssignments().size()
	    );
	    
	    model.addAttribute(
	            "assignedAssignments",
	            assignedAssignments
	    );

	    model.addAttribute(
	            "submittedAssignments",
	            submittedAssignments
	    );

	    model.addAttribute(
	            "pendingAssignments",
	            pendingAssignments
	    );
	    model.addAttribute(
	            "appliedDrives",
	            appliedDrives
	    );

	    model.addAttribute(
	            "selectedDrives",
	            selectedDrives
	    );

	    model.addAttribute(
	            "offerReceived",
	            offerReceived
	    );
	    
	    model.addAttribute(
	            "upcomingExamCount",
	            upcomingExams.size()
	    );

	    model.addAttribute(
	            "nextExamName",
	            nextExamName
	    );
	    

	    return "layout/student-layout";
	}
}