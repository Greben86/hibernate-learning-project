package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.QuizDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.QuizMapper;
import spring.hibernate.learning.LearningApp.repository.ModuleRepository;
import spring.hibernate.learning.LearningApp.repository.QuizRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;
    private final QuizMapper mapper;

    public List<QuizDTO> getAll() {
        return quizRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public QuizDTO getById(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new LogicException("Тест ID="+id+" не найден");
        }
        return mapper.fromEntity(quizRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuizDTO addQuiz(QuizDTO dto) {
        final var module = moduleRepository.findById(dto.moduleId())
                .orElseThrow(() -> new LogicException("Модуль ID="+dto.moduleId()+" не найден"));
        final var newEntity = mapper.toEntity(dto, module);
        return mapper.fromEntity(quizRepository.save(newEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }
}
