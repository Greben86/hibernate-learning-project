package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.AnswerOptionDTO;
import spring.hibernate.learning.LearningApp.dto.QuestionDTO;
import spring.hibernate.learning.LearningApp.service.AnswerOptionService;
import spring.hibernate.learning.LearningApp.service.QuestionService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "REST API: Вопросы")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerOptionService answerOptionService;

    @Operation(summary = "Список вопросов")
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDTO> getAll() {
        log.info("Список вопросов");
        return questionService.getAll();
    }

    @Operation(summary = "Вопрос по ID")
    @GetMapping("/question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDTO getById(@PathVariable Long id) {
        log.info("Вопрос по ID");
        return questionService.getById(id);
    }

    @Operation(summary = "Добавить вопрос")
    @PostMapping("/question")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public QuestionDTO create(@RequestBody QuestionDTO dto) {
        log.info("Добавить вопрос");
        return questionService.addQuestion(dto);
    }

    @Operation(summary = "Удалить вопрос")
    @DeleteMapping("/question/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить вопрос");
        if (questionService.getById(id) != null) {
            questionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Список вариантов")
    @GetMapping("/question/{id}/answers")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public List<AnswerOptionDTO> getAnswer(@PathVariable Long id) {
        log.info("Список вариантов");
        return answerOptionService.getAll(id);
    }

    @Operation(summary = "Вариант ответа по ID")
    @GetMapping("/answer/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public AnswerOptionDTO getAnswerById(@PathVariable Long id) {
        log.info("Вариант ответа по ID");
        return answerOptionService.getById(id);
    }

    @Operation(summary = "Добавить вариант")
    @PostMapping("/answer")
    @ResponseStatus(HttpStatus.OK)
    public AnswerOptionDTO addSubmission(@RequestBody AnswerOptionDTO dto) {
        log.info("Добавить вариант");
        return answerOptionService.addQuestion(dto);
    }

    @Operation(summary = "Удалить вариант")
    @DeleteMapping("/answer/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Удалить вариант");
        if (answerOptionService.getById(id) != null) {
            answerOptionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
