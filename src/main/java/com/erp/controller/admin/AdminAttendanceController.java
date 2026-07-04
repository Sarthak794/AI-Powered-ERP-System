package com.erp.controller.admin;

import com.erp.service.AdminAttendanceService;
import com.erp.service.AdminBatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminAttendanceController {

    private final AdminAttendanceService attendanceService;
    private final AdminBatchService batchService;

    public AdminAttendanceController(
            AdminAttendanceService attendanceService,
            AdminBatchService batchService
    ) {
        this.attendanceService = attendanceService;
        this.batchService = batchService;
    }

    // ✅ URL → /admin/attendance
    @GetMapping("/attendance")
    public String attendancePage(Model model) {

        model.addAttribute("batches", batchService.getAllBatches());

        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "admin/attendance");

        return "layout/admin-layout";
    }

    @PostMapping("/attendance/view")
    public String viewAttendance(
            @RequestParam Long batchId,
            @RequestParam(required = false) String date,
            Model model
    ) {
        model.addAttribute("batches", batchService.getAllBatches());
        model.addAttribute("selectedBatch", batchId);

        if (date != null && !date.isEmpty()) {
            model.addAttribute(
                    "records",
                    attendanceService.getByBatchAndDate(batchId, LocalDate.parse(date))
            );
            model.addAttribute("date", date);
        } else {
            model.addAttribute(
                    "records",
                    attendanceService.getByBatch(batchId)
            );
        }

        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "admin/attendance");

        return "layout/admin-layout";
    }
}
