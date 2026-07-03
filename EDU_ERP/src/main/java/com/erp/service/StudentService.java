package com.erp.service;

import com.erp.entity.Attendance;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import java.util.List;

@Service
public class StudentService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    

    public StudentService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            UserRepository userRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    private Student getCurrentStudentInternal() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Logged-in user not found"));

        return studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    public Student getCurrentStudent() {
        return getCurrentStudentInternal();
    }

    public List<Attendance> getMyAttendance() {

        Student student = getCurrentStudentInternal();

        return attendanceRepository
                .findByStudentIdOrderByDateDesc(student.getId());
    }

    public long getTotalClasses() {

        Student student = getCurrentStudentInternal();

        return attendanceRepository
                .countByStudentId(student.getId());
    }

    public long getPresentDays() {

        Student student = getCurrentStudentInternal();

        return attendanceRepository
                .countByStudentIdAndPresentTrue(student.getId());
    }

    public double getAttendancePercentage() {

        long total = getTotalClasses();
        long present = getPresentDays();

        if (total == 0) {
            return 0;
        }

        return Math.round(
                (present * 100.0 / total) * 100.0
        ) / 100.0;
    }

    public double getAttendancePercentage(Student student) {

        long total =
                attendanceRepository.countByStudentId(
                        student.getId());

        long present =
                attendanceRepository.countByStudentIdAndPresentTrue(
                        student.getId());

        if (total == 0) {
            return 0;
        }

        return Math.round(
                (present * 100.0 / total) * 100.0
        ) / 100.0;
    }

    public void updateStudentProfile(
            Student formStudent,
            MultipartFile photo
    ) {

        Student student =
                getCurrentStudent();

        student.setPhone(
                formStudent.getPhone());

        student.setAddress(
                formStudent.getAddress());

        student.setDob(
                formStudent.getDob());

        student.setGender(
                formStudent.getGender());

        try {

            if(photo != null &&
                    !photo.isEmpty()) {

                String fileName =
                        UUID.randomUUID() + "_" +
                        photo.getOriginalFilename();

                Path uploadPath =
                        Paths.get(
                                "uploads/profile"
                        );

                if(!Files.exists(uploadPath)) {

                    Files.createDirectories(
                            uploadPath
                    );

                }

                Files.copy(
                        photo.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING
                );

                student.setProfilePhoto(
                        fileName
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Photo upload failed"
            );

        }

        studentRepository.save(student);
    }
    public int getProfileCompletion(Student student) {

        int total = 8;
        int completed = 0;

        if(student.getPhone() != null &&
                !student.getPhone().isBlank())
            completed++;

        if(student.getDob() != null)
            completed++;

        if(student.getGender() != null &&
                !student.getGender().isBlank())
            completed++;

        if(student.getAddress() != null &&
                !student.getAddress().isBlank())
            completed++;

        if(student.getResumePath() != null)
            completed++;

        if(student.getProfilePhoto() != null)
            completed++;

        if(student.getBatch() != null)
            completed++;

        if(student.getSpecialization() != null)
            completed++;

        return (completed * 100) / total;
    }
}