package com.erp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    long countByRole(Role role);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
