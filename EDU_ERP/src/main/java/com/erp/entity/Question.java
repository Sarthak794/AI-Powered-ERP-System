package com.erp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "exam_questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false)
    private int marks;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;
    
    @Column(name = "image_path")
    private String imagePath;

    
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(Long id, Exam exam, String text, int marks, List<Option> options) {
		super();
		this.id = id;
		this.exam = exam;
		this.text = text;
		this.marks = marks;
		this.options = options;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public String getImagePath() {
	    return imagePath;
	}

	public void setImagePath(String imagePath) {
	    this.imagePath = imagePath;
	}

    // getters & setters
}
