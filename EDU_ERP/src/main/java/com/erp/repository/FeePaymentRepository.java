package com.erp.repository;

import com.erp.entity.FeePayment;
import com.erp.entity.PaymentMode;
import com.erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByStudent(Student student);

    List<FeePayment> findAllByOrderByPaymentDateDesc();

    @Query("select coalesce(sum(f.amount),0) from FeePayment f")
    double sumTotalRevenue();
    
    @Query("SELECT SUM(f.amount) FROM FeePayment f")
    Double getTotalFees();

    @Query("SELECT SUM(f.amount) FROM FeePayment f")
    Double getTotalCollected();


    @Query("select coalesce(sum(f.amount),0) from FeePayment f where f.paymentDate = :date")
    double sumRevenueByDate(@Param("date") LocalDate date);

    @Query("""
        select coalesce(sum(f.amount),0)
        from FeePayment f
        where year(f.paymentDate)=:year
        and month(f.paymentDate)=:month
    """)
    double sumRevenueByMonth(@Param("year") int year,
                             @Param("month") int month);

    @Query("""
        select coalesce(sum(f.amount), 0)
        from FeePayment f
        where f.paymentMode = :mode
    """)
    double sumByPaymentMode(@Param("mode") PaymentMode mode);

    @Query("""
        select coalesce(sum(f.amount),0)
        from FeePayment f
        where month(f.paymentDate) = month(current_date)
    """)
    Double totalThisMonth();
}
