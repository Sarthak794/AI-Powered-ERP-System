package com.erp.repository;

import com.erp.entity.Notification;
import com.erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStudentOrderByCreatedAtDesc(Student student);

    long countByStudentAndReadStatusFalse(Student student);

}