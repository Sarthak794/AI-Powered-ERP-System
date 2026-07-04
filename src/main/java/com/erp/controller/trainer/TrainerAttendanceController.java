package com.erp.controller.trainer;

import com.erp.entity.Batch;
import com.erp.entity.Student;
import com.erp.service.TrainerAttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/trainer/attendance")
public class TrainerAttendanceController {

    private final TrainerAttendanceService attendanceService;

    public TrainerAttendanceController(TrainerAttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ==================================
    // STEP 1: Attendance Home (Select Batch)
    // URL: /trainer/attendance
    // ==================================
    @GetMapping
    public String attendanceHome(Model model) {

        List<Batch> batches = attendanceService.getMyBatches();

        model.addAttribute("batches", batches);
        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "trainer/attendance");

        return "layout/trainer-layout";
    }

    // ==================================
    // STEP 2: Show Students of Selected Batch
    // URL: /trainer/attendance/batch
    // ==================================
    @PostMapping("/batch")
    public String selectBatch(

            @RequestParam Long batchId,

            @RequestParam(required = false) String date,

            Model model

    ) {

        LocalDate attendanceDate =
                (date == null || date.isBlank())
                        ? LocalDate.now()
                        : LocalDate.parse(date);

        List<Student> students =
                attendanceService.getStudentsByBatch(batchId);

        boolean alreadyMarked =
                attendanceService.isAttendanceMarked(
                        batchId,
                        attendanceDate
                );

        model.addAttribute(
                "attendanceMap",
                attendanceService.getAttendanceMap(
                        batchId,
                        attendanceDate
                )
        );

        model.addAttribute(
                "summary",
                attendanceService.getAttendanceSummary(
                        batchId,
                        attendanceDate
                )
        );

        model.addAttribute("alreadyMarked", alreadyMarked);
        model.addAttribute("students", students);
        model.addAttribute("batchId", batchId);
        model.addAttribute("date", attendanceDate);

        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "trainer/attendance-batch");

        return "layout/trainer-layout";
    }

    // ==================================
    // STEP 3: Mark Attendance
    // URL: /trainer/attendance/mark
    // ==================================
    @GetMapping("/batch/{id}")
    public String attendanceBatch(

            @PathVariable Long id,

            @RequestParam(required = false) String date,

            Model model

    ) {

        LocalDate attendanceDate =
                (date == null || date.isBlank())
                        ? LocalDate.now()
                        : LocalDate.parse(date);

        model.addAttribute(
                "students",
                attendanceService.getStudentsByBatch(id)
        );

        model.addAttribute(
                "attendanceMap",
                attendanceService.getAttendanceMap(
                        id,
                        attendanceDate
                )
        );

        model.addAttribute(
                "summary",
                attendanceService.getAttendanceSummary(
                        id,
                        attendanceDate
                )
        );

        model.addAttribute(
                "alreadyMarked",
                attendanceService.isAttendanceMarked(
                        id,
                        attendanceDate
                )
        );

        model.addAttribute("batchId", id);
        model.addAttribute("date", attendanceDate);

        model.addAttribute("activePage", "attendance");
        model.addAttribute("content", "trainer/attendance-batch");

        return "layout/trainer-layout";
    }
    @PostMapping("/mark")
    public String markAttendance(

            @RequestParam Long batchId,

            @RequestParam String date,

            @RequestParam(required = false)
            List<Long> presentStudentIds,

            RedirectAttributes ra

    ) {

        try {

            attendanceService.markBulkAttendance(

                    batchId,

                    LocalDate.parse(date),

                    presentStudentIds

            );

            if (attendanceService.isAttendanceMarked(
                    batchId,
                    LocalDate.parse(date))) {

                ra.addFlashAttribute(
                        "success",
                        "Attendance updated successfully."
                );

            } else {

                ra.addFlashAttribute(
                        "success",
                        "Attendance saved successfully."
                );

            }

        }

        catch (Exception ex) {

            ra.addFlashAttribute(
                    "error",
                    ex.getMessage()
            );

        }

        return "redirect:/trainer/attendance/batch/"
                + batchId
                + "?date="
                + date;

    }
}
