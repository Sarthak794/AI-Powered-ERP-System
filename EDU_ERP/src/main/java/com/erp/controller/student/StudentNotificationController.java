package com.erp.controller.student;

import com.erp.entity.Student;
import com.erp.service.NotificationService;
import com.erp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentNotificationController {

    private final NotificationService notificationService;
    private final StudentService studentService;

    public StudentNotificationController(
            NotificationService notificationService,
            StudentService studentService) {

        this.notificationService = notificationService;
        this.studentService = studentService;
    }

    @GetMapping("/student/notifications")
    public String notifications(Model model) {

        Student student =
                studentService.getCurrentStudent();

        model.addAttribute(
                "notifications",
                notificationService.getStudentNotifications(student)
        );

        model.addAttribute(
                "unreadCount",
                notificationService.getUnreadCount(student)
        );

        return "student/notifications";
    }

    @GetMapping("/student/notifications/read/{id}")
    public String markAsRead(@PathVariable Long id) {

        notificationService.markAsRead(id);

        return "redirect:/student/notifications";
    }

    @GetMapping("/student/notifications/read-all")
    public String markAllAsRead() {

        Student student =
                studentService.getCurrentStudent();

        notificationService.markAllAsRead(student);

        return "redirect:/student/notifications";
    }
}