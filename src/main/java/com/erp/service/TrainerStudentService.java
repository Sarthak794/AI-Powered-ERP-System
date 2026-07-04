package com.erp.service;

import com.erp.dto.BatchStudentDto;
import com.erp.entity.ExamAttempt;
import com.erp.entity.Student;
import com.erp.entity.User;
import com.erp.repository.AttendanceRepository;
import com.erp.repository.ExamAttemptRepository;
import com.erp.repository.StudentRepository;
import com.erp.repository.UserRepository;
import com.erp.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerStudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final ExamAttemptRepository examAttemptRepository;

    public TrainerStudentService(
            StudentRepository studentRepository,
            UserRepository userRepository,
            AttendanceRepository attendanceRepository,
            ExamAttemptRepository examAttemptRepository
    ) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.examAttemptRepository = examAttemptRepository;
    }

    public List<BatchStudentDto> getMyStudents() {

        String username = SecurityUtil.getCurrentUsername();

        if (username == null) {
            throw new IllegalStateException("No logged-in user");
        }

        User trainer = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Trainer not found"));

        List<Student> students =
                studentRepository.findByBatchTrainerUser(trainer);

        List<BatchStudentDto> dtoList = new ArrayList<>();

        for (Student student : students) {

            BatchStudentDto dto = new BatchStudentDto();

            dto.setRollNumber(student.getRollNumber());
            dto.setName(student.getFullName());
            dto.setEmail(student.getEmail());
            dto.setPhone(student.getPhone());

            Double attendance =
                    attendanceRepository.getAttendancePercent(student.getId());

            dto.setAttendancePercentage(
                    attendance != null ? attendance : 0
            );

            List<ExamAttempt> attempts =
                    examAttemptRepository.findByStudentId(student.getId());

            int totalScore = attempts.stream()
                    .mapToInt(ExamAttempt::getScore)
                    .sum();

            dto.setExamScore(totalScore);

            dtoList.add(dto);
        }

        return dtoList;
    }
}