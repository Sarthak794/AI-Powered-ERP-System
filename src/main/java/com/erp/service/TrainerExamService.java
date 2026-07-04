package com.erp.service;

import com.erp.dto.ExamAnalyticsDTO;
import com.erp.dto.QuestionAnalyticsDTO;
import com.erp.entity.*;
import com.erp.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerExamService {

    private final ExamAttemptRepository examAttemptRepository;

    private final ExamRepository examRepository;
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ExamAnswerRepository examAnswerRepository;

    public TrainerExamService(
            ExamRepository examRepository,
            BatchRepository batchRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository,
            ExamAnswerRepository examAnswerRepository,
            ExamAttemptRepository examAttemptRepository
    ) {
    	this.examAttemptRepository = examAttemptRepository;
		this.examRepository = examRepository;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
		this.examAnswerRepository = examAnswerRepository;
    }

    private User getCurrentTrainer() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<Exam> getMyExams() {

        List<Exam> exams =
                examRepository.findByTrainer(
                        getCurrentTrainer());

        for (Exam exam : exams) {

            exam.setAttemptCount(
                    examAttemptRepository
                            .countByExamId(
                                    exam.getId()
                            )
            );
        }

        return exams;
    }

    public List<Batch> getMyBatches() {
        return batchRepository.findByTrainerUser(getCurrentTrainer());
    }

    public void createExam(

            String title,
            String description,
            int duration,
            int totalMarks,
            Long batchId,

            LocalDate startDate,
            LocalDate endDate

    ) {
        Exam exam = new Exam();
        exam.setTitle(title);
        exam.setDescription(description);
        exam.setDurationMinutes(duration);
        exam.setTotalMarks(totalMarks);
        exam.setTrainer(getCurrentTrainer());
        exam.setBatch(batchRepository.findById(batchId).orElseThrow());
        exam.setStartDate(startDate);
        exam.setEndDate(endDate);

        examRepository.save(exam);
    }

    public Exam getExam(Long id) {
        return examRepository.findById(id).orElseThrow();
    }

    public void addQuestion(
            Long examId,
            String text,
            int marks,
            MultipartFile questionImage,
            String a,
            String b,
            String c,
            String d,
            String correct
    ) throws IOException {

        Exam exam = getExam(examId);

        Question q = new Question();

        q.setExam(exam);
        q.setText(text);
        q.setMarks(marks);

        // ==========================
        // IMAGE UPLOAD
        // ==========================
        if (questionImage != null &&
            !questionImage.isEmpty()) {

            String uploadDir =
                    "uploads/questions/";

            Files.createDirectories(
                    Paths.get(uploadDir)
            );

            String filename =
                    UUID.randomUUID()
                    + "_"
                    + questionImage.getOriginalFilename();

            Path filepath =
                    Paths.get(uploadDir, filename);

            Files.copy(
                    questionImage.getInputStream(),
                    filepath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            q.setImagePath(
                    "/uploads/questions/" + filename
            );
        }
        if (questionImage != null &&
        	    !questionImage.isEmpty()) {

        	    String contentType =
        	            questionImage.getContentType();

        	    if (!contentType.startsWith("image/")) {
        	        throw new RuntimeException(
        	                "Only image files are allowed"
        	        );
        	    }
        	}
        questionRepository.save(q);

        saveOption(q, a, correct.equals("A"));
        saveOption(q, b, correct.equals("B"));
        saveOption(q, c, correct.equals("C"));
        saveOption(q, d, correct.equals("D"));
    }

    private void saveOption(Question q, String text, boolean correct) {
        Option o = new Option();
        o.setQuestion(q);
        o.setText(text);
        o.setCorrect(correct);
        optionRepository.save(o);
    }
    
    public List<QuestionAnalyticsDTO>
    getQuestionAnalytics(Long examId) {

        Exam exam = getExam(examId);

        List<QuestionAnalyticsDTO> result =
                new ArrayList<>();

        for (Question q : exam.getQuestions()) {

            long total =
                    examAnswerRepository
                            .countByQuestionId(q.getId());

            long correct =
                    examAnswerRepository
                            .countByQuestionIdAndSelectedOptionCorrectTrue(
                                    q.getId()
                            );

            double accuracy = 0;

            if (total > 0) {
                accuracy =
                        (correct * 100.0) / total;
            }

            result.add(
                    new QuestionAnalyticsDTO(
                            q.getText(),
                            total,
                            correct,
                            Math.round(accuracy * 100.0) / 100.0
                    )
            );
        }

        return result;
    }
    
    public ExamAnalyticsDTO getExamAnalytics(Long examId) {

        List<ExamAttempt> attempts =
                examAttemptRepository.findByExamId(examId);

        ExamAnalyticsDTO dto =
                new ExamAnalyticsDTO();

        dto.setTotalAttempts(attempts.size());

        if(attempts.isEmpty())
            return dto;

        double avg =
                attempts.stream()
                .mapToInt(ExamAttempt::getScore)
                .average()
                .orElse(0);

        int highest =
                attempts.stream()
                .mapToInt(ExamAttempt::getScore)
                .max()
                .orElse(0);

        int lowest =
                attempts.stream()
                .mapToInt(ExamAttempt::getScore)
                .min()
                .orElse(0);

        Exam exam = getExam(examId);

        double passMarks = exam.getTotalMarks() * 0.40;

        long passed = attempts.stream()
                .filter(a -> a.getScore() >= passMarks)
                .count();
        long failed =
                attempts.size() - passed;

        dto.setAverageScore(avg);
        dto.setHighestScore(highest);
        dto.setLowestScore(lowest);
        dto.setPassedStudents(passed);
        dto.setFailedStudents(failed);

        dto.setPassPercentage(
                (passed * 100.0)
                        / attempts.size()
        );

        return dto;
    }
    
    public void toggleExamStatus(Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow();

        if(!exam.isPublished()
            && exam.getQuestions().isEmpty()) {

            throw new RuntimeException(
                    "Add at least one question before publishing."
            );
        }

        exam.setPublished(!exam.isPublished());

        examRepository.save(exam);
    }
    
    public void publishExam(Long id) {

        Exam exam =
                examRepository.findById(id)
                        .orElseThrow();

        if(exam.getQuestions() == null ||
           exam.getQuestions().isEmpty()) {

            throw new RuntimeException(
                    "Cannot publish exam without questions."
            );
        }

        exam.setPublished(true);

        examRepository.save(exam);
    }

    public void unpublishExam(Long id) {

        Exam exam =
                examRepository.findById(id)
                        .orElseThrow();

        exam.setPublished(false);

        examRepository.save(exam);
    }
    
    public void updateExam(
            Long id,
            String title,
            String description,
            int durationMinutes,
            int totalMarks,
            LocalDate startDate,
            LocalDate endDate,
            Long batchId
    ) {

        Exam exam = examRepository.findById(id)
                .orElseThrow();

        exam.setTitle(title);
        exam.setDescription(description);
        exam.setDurationMinutes(durationMinutes);
        exam.setTotalMarks(totalMarks);
        exam.setStartDate(startDate);
        exam.setEndDate(endDate);

        exam.setBatch(
                batchRepository.findById(batchId)
                        .orElseThrow()
        );

        examRepository.save(exam);
    }
    
    public void updateQuestion(
            Long id,
            String text,
            int marks,
            String a,
            String b,
            String c,
            String d,
            String correct
    ){

        Question q =
                questionRepository.findById(id)
                        .orElseThrow();


        q.setText(text);
        q.setMarks(marks);


        List<Option> options =
                q.getOptions();


        options.get(0).setText(a);
        options.get(0).setCorrect(correct.equals("A"));


        options.get(1).setText(b);
        options.get(1).setCorrect(correct.equals("B"));


        options.get(2).setText(c);
        options.get(2).setCorrect(correct.equals("C"));


        options.get(3).setText(d);
        options.get(3).setCorrect(correct.equals("D"));


        questionRepository.save(q);
    }
    
    public void deleteQuestion(Long id){

        questionRepository.deleteById(id);

    }

}
