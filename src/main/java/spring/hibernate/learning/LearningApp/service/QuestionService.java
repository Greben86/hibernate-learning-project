package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.QuestionDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.QuestionMapper;
import spring.hibernate.learning.LearningApp.repository.QuestionRepository;
import spring.hibernate.learning.LearningApp.repository.QuizRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;

    public List<QuestionDTO> getAll() {
        return questionRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public QuestionDTO getById(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new LogicException("Вопрос ID="+id+" не найден");
        }
        return mapper.fromEntity(questionRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionDTO addQuestion(QuestionDTO dto) {
        final var quiz = quizRepository.findById(dto.quizId())
                .orElseThrow(() -> new LogicException("Тест ID="+dto.quizId()+" не найден"));
        final var newEntity = mapper.toEntity(dto, quiz);
        return mapper.fromEntity(questionRepository.save(newEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
