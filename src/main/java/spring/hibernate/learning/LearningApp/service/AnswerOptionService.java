package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.AnswerOptionDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.AnswerOptionMapper;
import spring.hibernate.learning.LearningApp.repository.AnswerOptionRepository;
import spring.hibernate.learning.LearningApp.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionMapper mapper;

    public List<AnswerOptionDTO> getAll(Long id) {
        final var question = questionRepository.findById(id)
                .orElseThrow(() -> new LogicException("Вопрос ID="+id+" не найден"));
        return question.getAnswerOptions().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public AnswerOptionDTO getById(Long id) {
        if (!answerOptionRepository.existsById(id)) {
            throw new LogicException("Вариант ответа ID="+id+" не найден");
        }
        return mapper.fromEntity(answerOptionRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AnswerOptionDTO addQuestion(AnswerOptionDTO dto) {
        final var question = questionRepository.findById(dto.questionId())
                .orElseThrow(() -> new LogicException("Вопрос ID="+dto.questionId()+" не найден"));
        final var newEntity = mapper.toEntity(dto, question);
        return mapper.fromEntity(answerOptionRepository.save(newEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        answerOptionRepository.deleteById(id);
    }
}
