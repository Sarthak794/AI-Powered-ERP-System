package com.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exam_answers")
public class ExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private ExamAttempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "selected_option_id", nullable = false)
    private Option selectedOption;

	public ExamAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamAnswer(Long id, ExamAttempt attempt, Question question, Option selectedOption) {
		super();
		this.id = id;
		this.attempt = attempt;
		this.question = question;
		this.selectedOption = selectedOption;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExamAttempt getAttempt() {
		return attempt;
	}

	public void setAttempt(ExamAttempt attempt) {
		this.attempt = attempt;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Option getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(Option selectedOption) {
		this.selectedOption = selectedOption;
	}

    // getters & setters
}
