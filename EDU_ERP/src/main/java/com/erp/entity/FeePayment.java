package com.erp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "fee_payments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "transaction_id")
})
@Getter
@Setter
@NoArgsConstructor
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The student who made the payment
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // The accountant who recorded the payment (optional)
    @ManyToOne
    @JoinColumn(name = "accountant_id")
    private Employee accountant;

    // Payment amount
    @Column(nullable = false)
    private Double amount;

    // Date of payment
    @Column(nullable = false)
    private LocalDate paymentDate;

    // Mode of payment (CASH, CHEQUE, ONLINE etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;

    // Unique transaction ID for tracking
    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    // Locked after save to prevent edits
    @Column(nullable = false)
    private boolean locked = true;

    // All-args constructor
    public FeePayment(Long id, Student student, Employee accountant, Double amount,
                      LocalDate paymentDate, PaymentMode paymentMode, String transactionId, boolean locked) {
        this.id = id;
        this.student = student;
        this.accountant = accountant;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.transactionId = transactionId;
        this.locked = locked;
    }

	public FeePayment() {
		super();
		// TODO Auto-generated constructor stub
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

	public Employee getAccountant() {
		return accountant;
	}

	public void setAccountant(Employee accountant) {
		this.accountant = accountant;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
