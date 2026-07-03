package com.erp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    private int amount;
    private LocalDateTime paidAt;

    @ManyToOne
    private User accountant;

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Long id, Student student, int amount, LocalDateTime paidAt, User accountant) {
		super();
		this.id = id;
		this.student = student;
		this.amount = amount;
		this.paidAt = paidAt;
		this.accountant = accountant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	public User getAccountant() {
		return accountant;
	}

	public void setAccountant(User accountant) {
		this.accountant = accountant;
	}

    // getters & setters
}

