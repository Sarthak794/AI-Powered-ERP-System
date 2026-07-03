package com.erp.service;

import com.erp.entity.Notification;
import com.erp.entity.Student;
import com.erp.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(Student student,
                                   String title,
                                   String message,
                                   String type) {

        Notification notification = new Notification();

        notification.setStudent(student);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setReadStatus(false);

        notificationRepository.save(notification);
    }

    public List<Notification> getStudentNotifications(Student student) {
        return notificationRepository.findByStudentOrderByCreatedAtDesc(student);
    }

    public long getUnreadCount(Student student) {
        return notificationRepository
                .countByStudentAndReadStatusFalse(student);
    }

    public void markAsRead(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found"));

        notification.setReadStatus(true);

        notificationRepository.save(notification);
    }

    public void markAllAsRead(Student student) {

        List<Notification> notifications =
                notificationRepository
                        .findByStudentOrderByCreatedAtDesc(student);

        notifications.forEach(notification ->
                notification.setReadStatus(true));

        notificationRepository.saveAll(notifications);
    }
}