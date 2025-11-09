package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.QuizSubmissionDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.QuizSubmissionMapper;
import spring.hibernate.learning.LearningApp.repository.QuizRepository;
import spring.hibernate.learning.LearningApp.repository.QuizSubmissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizRepository quizRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final QuizSubmissionMapper mapper;
    private final UserService userService;

    public List<QuizSubmissionDTO> getAll(Long id) {
        final var quiz = quizRepository.findById(id)
                .orElseThrow(() -> new LogicException("Тест ID="+id+" не найден"));
        return quiz.getQuizSubmissions().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public QuizSubmissionDTO getById(Long id) {
        if (!quizSubmissionRepository.existsById(id)) {
            throw new LogicException("Результат ID="+id+" не найден");
        }
        return mapper.fromEntity(quizSubmissionRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuizSubmissionDTO addQuizSubmission(QuizSubmissionDTO dto) {
        final var user = userService.getCurrentUser();
        final var quiz = quizRepository.findById(dto.quizId())
                .orElseThrow(() -> new LogicException("Тест ID="+dto.quizId()+" не найден"));
        final var newEntity = mapper.toEntity(dto, quiz, user);
        return mapper.fromEntity(quizSubmissionRepository.save(newEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        quizSubmissionRepository.deleteById(id);
    }
}
