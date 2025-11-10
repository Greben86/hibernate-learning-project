package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.QuestionDTO;
import spring.hibernate.learning.LearningApp.dto.QuizAnswerRequest;
import spring.hibernate.learning.LearningApp.dto.QuizAnswerResponse;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.QuestionMapper;
import spring.hibernate.learning.LearningApp.map.QuizAnswerMapper;
import spring.hibernate.learning.LearningApp.repository.AnswerOptionRepository;
import spring.hibernate.learning.LearningApp.repository.QuestionRepository;
import spring.hibernate.learning.LearningApp.repository.QuizAnswerRepository;
import spring.hibernate.learning.LearningApp.repository.QuizRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserService userService;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuestionMapper questionMapper;
    private final QuizAnswerMapper quizAnswerMapper;

    public List<QuestionDTO> getAll() {
        return questionRepository.findAll().stream()
                .map(questionMapper::fromEntity)
                .toList();
    }

    public QuestionDTO getById(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new LogicException("Вопрос ID="+id+" не найден");
        }
        return questionMapper.fromEntity(questionRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuestionDTO addQuestion(QuestionDTO dto) {
        final var quiz = quizRepository.findById(dto.quizId())
                .orElseThrow(() -> new LogicException("Тест ID="+dto.quizId()+" не найден"));
        final var newEntity = questionMapper.toEntity(dto, quiz);
        return questionMapper.fromEntity(questionRepository.save(newEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public QuizAnswerResponse addAnswer(QuizAnswerRequest request) {
        final var user = userService.getCurrentUser();
        if (!questionRepository.existsById(request.questionId())) {
            throw new LogicException("Вопрос ID="+request.questionId()+" не найден");
        }
        final var question = questionRepository.getReferenceById(request.questionId());

        if (!answerOptionRepository.existsById(request.optionId())) {
            throw new LogicException("Вариант ответа ID="+request.optionId()+" не найден");
        }
        final var option = answerOptionRepository.getReferenceById(request.optionId());

        final var newEntity = quizAnswerMapper.toEntity(request, user, question, option);
        return quizAnswerMapper.fromEntity(quizAnswerRepository.save(newEntity));
    }

    public List<QuizAnswerResponse> findQuizAnswers(Long questionId) {
        final var user = userService.getCurrentUser();
        if (!questionRepository.existsById(questionId)) {
            throw new LogicException("Вопрос ID="+questionId+" не найден");
        }
        final var question = questionRepository.getReferenceById(questionId);

        return quizAnswerRepository.findByStudentAndQuestion(user, question).stream()
                .map(quizAnswerMapper::fromEntity)
                .toList();
    }

    public List<QuizAnswerResponse> findQuizAnswers(String student, Long questionId) {
        final var user = userService.getByUsername(student);
        if (!questionRepository.existsById(questionId)) {
            throw new LogicException("Вопрос ID="+questionId+" не найден");
        }
        final var question = questionRepository.getReferenceById(questionId);

        return quizAnswerRepository.findByStudentAndQuestion(user, question).stream()
                .map(quizAnswerMapper::fromEntity)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
