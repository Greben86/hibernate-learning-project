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
import spring.hibernate.learning.LearningApp.dto.QuizDTO;
import spring.hibernate.learning.LearningApp.dto.QuizSubmissionDTO;
import spring.hibernate.learning.LearningApp.service.QuizService;
import spring.hibernate.learning.LearningApp.service.QuizSubmissionService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/quizes")
@RequiredArgsConstructor
@Tag(name = "REST API: Тесты")
public class QuizController {

    private final QuizService quizService;
    private final QuizSubmissionService quizSubmissionService;

    @Operation(summary = "Список тестов")
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<QuizDTO> getAll() {
        log.info("Список тестов");
        return quizService.getAll();
    }

    @Operation(summary = "Тест по ID")
    @GetMapping("/quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuizDTO getById(@PathVariable Long id) {
        log.info("Тест по ID");
        return quizService.getById(id);
    }

    @Operation(summary = "Добавить тест")
    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public QuizDTO create(@RequestBody QuizDTO dto) {
        log.info("Добавить тест");
        return quizService.addQuiz(dto);
    }

    @Operation(summary = "Удалить тест")
    @DeleteMapping("/quiz/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить тест");
        if (quizService.getById(id) != null) {
            quizService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Список результатов")
    @GetMapping("/quiz/{id}/submissions")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public List<QuizSubmissionDTO> getSubmissions(@PathVariable Long id) {
        log.info("Список результатов");
        return quizSubmissionService.getAll(id);
    }

    @Operation(summary = "Результат по ID")
    @GetMapping("/submission/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public QuizSubmissionDTO getSubmissionById(@PathVariable Long id) {
        log.info("Результат по ID");
        return quizSubmissionService.getById(id);
    }

    @Operation(summary = "Добавить результат")
    @PostMapping("/submission")
    @ResponseStatus(HttpStatus.OK)
    public QuizSubmissionDTO addSubmission(@RequestBody QuizSubmissionDTO dto) {
        log.info("Добавить результат");
        return quizSubmissionService.addQuizSubmission(dto);
    }

    @Operation(summary = "Удалить результат")
    @DeleteMapping("/submission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Удалить результат");
        if (quizSubmissionService.getById(id) != null) {
            quizSubmissionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
