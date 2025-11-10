package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.FeedbackDTO;
import spring.hibernate.learning.LearningApp.dto.SubmissionDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.SubmissionMapper;
import spring.hibernate.learning.LearningApp.repository.AssignmentRepository;
import spring.hibernate.learning.LearningApp.repository.SubmissionRepository;
import spring.hibernate.learning.LearningApp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final SubmissionMapper mapper;
    private final UserService userService;
    private final EmailNotificationService emailNotificationService;

    public List<SubmissionDTO> getAll() {
        return submissionRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public SubmissionDTO getById(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new LogicException("Ответ ID="+id+" не найден");
        }
        return mapper.fromEntity(submissionRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SubmissionDTO addSubmission(SubmissionDTO dto) {
        final var user = userService.getCurrentUser();
        final var assignment = assignmentRepository.findByTitle(dto.assignment())
                .orElseThrow(() -> new LogicException("Задание "+dto.assignment()+" не найдено"));
        final var entity = mapper.toEntity(dto, assignment, user);
        return mapper.fromEntity(submissionRepository.save(entity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        submissionRepository.deleteById(id);
    }

    public List<SubmissionDTO> findByAssignment(Long assignmentId) {
        final var assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new LogicException("Задание ID="+assignmentId+" не найдено"));
        return assignment.getSubmissions().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public List<SubmissionDTO> findByStudent(Long studentId) {
        final var student = userRepository.findById(studentId)
                .orElseThrow(() -> new LogicException("Студент ID="+studentId+" не найден"));
        return student.getSubmissions().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SubmissionDTO grade(Long id, FeedbackDTO dto) {
        if (!submissionRepository.existsById(id)) {
            throw new LogicException("Ответ ID="+id+" не найден");
        }

        final var entity = submissionRepository.getReferenceById(id);
        entity.setScore(dto.score());
        entity.setFeedback(dto.feedback());

        if (entity.getStudent().getProfile() != null) {
            emailNotificationService.sendNotification(entity);
        }

        return mapper.fromEntity(submissionRepository.save(entity));
    }
}
