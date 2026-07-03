package com.erp.service;

import com.erp.entity.Center;
import com.erp.entity.Qualification;
import com.erp.entity.Specialization;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.enums.Role;
import com.erp.repository.CenterRepository;
import com.erp.repository.QualificationRepository;
import com.erp.repository.SpecializationRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminStudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CenterRepository centerRepository;
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;

    // Explicit constructor for dependency injection
    public AdminStudentService(
            StudentRepository studentRepository,
            UserRepository userRepository,
            CenterRepository centerRepository,
            QualificationRepository qualificationRepository,
            SpecializationRepository specializationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
        this.qualificationRepository = qualificationRepository;
        this.specializationRepository = specializationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================== STUDENT OPERATIONS ==================
    public void createStudent(
            String fullName,
            String rollNumber,
            String email,
            String phone,
            String username,
            String password,
            Long centerId,
            Long qualificationId,
            Long specializationId
    ) {
        // Check duplicate username
    	if (userRepository.findByUsername(username).isPresent()) {
    	    throw new IllegalArgumentException("Username already exists");
    	}

    	if (userRepository.existsByEmail(email)) {
    	    throw new IllegalArgumentException("Email already exists");
    	}

    	if (studentRepository.existsByRollNumber(rollNumber)) {
    	    throw new IllegalArgumentException("Roll number already exists");
    	}


        // Create User
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.STUDENT);
        user.setEnabled(true);

        userRepository.save(user);

        // Create Student
        Student student = new Student();
        student.setFullName(fullName);
        student.setRollNumber(rollNumber);
        student.setEmail(email);
        student.setPhone(phone);
        student.setUser(user);

        student.setCenter(
                centerRepository.findById(centerId)
                        .orElseThrow(() -> new RuntimeException("Center not found"))
        );

        student.setQualification(
                qualificationRepository.findById(qualificationId)
                        .orElseThrow(() -> new RuntimeException("Qualification not found"))
        );

        student.setSpecialization(
                specializationRepository.findById(specializationId)
                        .orElseThrow(() -> new RuntimeException("Specialization not found"))
        );

        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // ================== DROPDOWN HELPERS ==================
    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    public List<Qualification> getAllQualifications() {
        return qualificationRepository.findAll();
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }
}
