package com.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trainer_salary_payments")
public class TrainerSalaryPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trainer trainer;

    private int amount;

    private LocalDate paymentDate;

    private String month; // e.g. 2026-01

    @ManyToOne
    private User accountant;

	public TrainerSalaryPayment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerSalaryPayment(Long id, Trainer trainer, int amount, LocalDate paymentDate, String month,
			User accountant) {
		super();
		this.id = id;
		this.trainer = trainer;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.month = month;
		this.accountant = accountant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public User getAccountant() {
		return accountant;
	}

	public void setAccountant(User accountant) {
		this.accountant = accountant;
	}

    // getters & setters
}
