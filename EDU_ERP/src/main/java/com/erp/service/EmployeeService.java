package com.erp.service;

import com.erp.entity.Center;
import com.erp.entity.Employee;
import com.erp.entity.User;
import com.erp.enums.Role;
import com.erp.repository.EmployeeRepository;
import com.erp.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(UserRepository userRepository,
                           EmployeeRepository employeeRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createEmployee(String fullName,
                               String email,
                               String rawPassword,
                               Role role,
                               Center center,
                               String designation,
                               Double salary) {

        if (userRepository.findByUsername(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setEnabled(true);

        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setCenter(center);
        employee.setDesignation(designation);
        employee.setSalary(salary);

        employeeRepository.save(employee);
    }
}
