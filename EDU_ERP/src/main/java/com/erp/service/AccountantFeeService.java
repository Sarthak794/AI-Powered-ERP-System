package com.erp.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.erp.entity.Employee;
import com.erp.entity.FeePayment;
import com.erp.entity.PaymentMode;
import com.erp.entity.Student;
import com.erp.repository.EmployeeRepository;
import com.erp.repository.FeePaymentRepository;
import com.erp.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountantFeeService {

    private final FeePaymentRepository feeRepo;
    private final StudentRepository studentRepo;
    private final EmployeeRepository employeeRepo;

    public AccountantFeeService(FeePaymentRepository feeRepo,
                                StudentRepository studentRepo,
                                EmployeeRepository employeeRepo) {
        this.feeRepo = feeRepo;
        this.studentRepo = studentRepo;
        this.employeeRepo = employeeRepo;
    }

    public FeePayment collectFee(String accountantUsername,
                                 Long studentId,
                                 Double amount,
                                 PaymentMode paymentMode) {

        Employee accountant = employeeRepo
                .findByUserUsername(accountantUsername)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        FeePayment payment = new FeePayment();
        payment.setStudent(student);
        payment.setAccountant(accountant);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMode(paymentMode);
        payment.setLocked(true);

        return feeRepo.save(payment);
    }
}
