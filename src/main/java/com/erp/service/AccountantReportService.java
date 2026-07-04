package com.erp.service;

import com.erp.entity.Fee;
import com.erp.entity.Payment;
import com.erp.repository.FeeRepository;
import com.erp.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountantReportService {

    private final PaymentRepository paymentRepository;
    private final FeeRepository feeRepository;

    public AccountantReportService(
            PaymentRepository paymentRepository,
            FeeRepository feeRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.feeRepository = feeRepository;
    }

    // 📅 Monthly Collection
    public int getMonthlyCollection(YearMonth month) {
        return paymentRepository.findAll().stream()
                .filter(p -> YearMonth.from(p.getPaidAt()).equals(month))
                .mapToInt(Payment::getAmount)
                .sum();
    }

    // 📚 Batch-wise Summary
    public Map<String, Integer> getBatchWiseCollection() {
        return paymentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStudent().getBatch().getName(),
                        Collectors.summingInt(Payment::getAmount)
                ));
    }

    // 💰 Overall Pending Amount
    public int getTotalPending() {
        return feeRepository.findAll().stream()
                .mapToInt(Fee::getPendingAmount)
                .sum();
    }
}
	