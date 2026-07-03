package com.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "exam_attempts",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"exam_id", "student_id"})
    }
)
public class ExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private int score;

    private LocalDateTime submittedAt;

    @OneToMany(
    	    mappedBy = "attempt",
    	    cascade = CascadeType.ALL,
    	    fetch = FetchType.EAGER
    	)
    	private List<ExamAnswer> answers;

	public ExamAttempt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamAttempt(Long id, Exam exam, Student student, int score, LocalDateTime submittedAt,
			List<ExamAnswer> answers) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.score = score;
		this.submittedAt = submittedAt;
		this.answers = answers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public List<ExamAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<ExamAnswer> answers) {
		this.answers = answers;
	}

    // getters & setters
}
