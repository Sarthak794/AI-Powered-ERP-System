package com.erp.controller.accountant;

import com.erp.service.AccountantReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@Controller
public class AccountantReportController {

    private final AccountantReportService reportService;

    public AccountantReportController(AccountantReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/accountant/reports")
    public String reports(
            @RequestParam(required = false) String month,
            Model model
    ) {

        YearMonth selectedMonth =
                (month != null) ? YearMonth.parse(month) : YearMonth.now();

        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute(
                "monthlyTotal",
                reportService.getMonthlyCollection(selectedMonth)
        );
        model.addAttribute(
                "batchWise",
                reportService.getBatchWiseCollection()
        );
        model.addAttribute(
                "totalPending",
                reportService.getTotalPending()
        );

        model.addAttribute("activePage", "reports");
        model.addAttribute("content", "accountant/reports");

        return "layout/accountant-layout";
    }
}
