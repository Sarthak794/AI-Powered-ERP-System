package com.erp.repository;

import com.erp.entity.Student;
import com.erp.entity.StudentOnlinePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentOnlinePaymentRepository
        extends JpaRepository<StudentOnlinePayment, Long> {

    List<StudentOnlinePayment> findByStudent(Student student);
}
