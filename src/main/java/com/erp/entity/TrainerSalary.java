package com.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer_salary")
public class TrainerSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Trainer trainer;

    private int monthlyAmount;

	public TrainerSalary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerSalary(Long id, Trainer trainer, int monthlyAmount) {
		super();
		this.id = id;
		this.trainer = trainer;
		this.monthlyAmount = monthlyAmount;
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

	public int getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(int monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

    // getters & setters
}
