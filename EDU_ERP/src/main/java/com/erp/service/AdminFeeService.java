package com.erp.service;

import com.erp.entity.Fee;
import com.erp.entity.Student;
import com.erp.repository.FeeRepository;
import com.erp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFeeService {

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;

    public AdminFeeService(
            FeeRepository feeRepository,
            StudentRepository studentRepository
    ) {
        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
    }

    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    public void allotFee(Long studentId, int totalAmount) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Fee fee = feeRepository.findByStudent(student)
                .orElseGet(() -> {
                    Fee f = new Fee();
                    f.setStudent(student);
                    f.setPaidAmount(0);
                    return f;
                });

        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Invalid fee amount");
        }

        fee.setTotalAmount(totalAmount);
        feeRepository.save(fee);
    }
}
