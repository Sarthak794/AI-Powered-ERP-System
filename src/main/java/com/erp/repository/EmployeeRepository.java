package com.erp.repository;

import com.erp.entity.Employee;
import com.erp.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find all employees by role (e.g., TRAINER, ACCOUNTANT)
    List<Employee> findByUserRole(Role role);

    // Find by employee ID
    Optional<Employee> findByUserId(Long userId);

    // ✅ Find by username (needed in AccountantFeeService)
    Optional<Employee> findByUserUsername(String username);
    
    @Query("SELECT e FROM Employee e WHERE e.user.role = 'TEACHER'")
    List<Employee> findAllTeachers();

}
