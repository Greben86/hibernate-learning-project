package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.hibernate.learning.LearningApp.dto.FeedbackDTO;
import spring.hibernate.learning.LearningApp.dto.SubmissionDTO;
import spring.hibernate.learning.LearningApp.service.SubmissionService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Tag(name = "REST API: Ответы на задания")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Operation(summary = "Добавить ответ на задание")
    @PostMapping("/submission")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionDTO submit(@RequestBody SubmissionDTO dto) {
        log.info("Добавить ответ на задание");
        return submissionService.addSubmission(dto);
    }

    @Operation(summary = "Просмотреть решение по id")
    @GetMapping("/submission/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionDTO getById(@PathVariable Long id) {
        log.info("Просмотреть решение по id");
        return submissionService.getById(id);
    }

    @Operation(summary = "Получить все решения по конкретному заданию")
    @GetMapping("/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public List<SubmissionDTO> getByAssignment(@PathVariable Long assignmentId) {
        log.info("Получить все решения по конкретному заданию");
        return submissionService.findByAssignment(assignmentId);
    }

    @Operation(summary = "Получить все решения конкретного студента")
    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionDTO> getByStudent(@PathVariable Long studentId) {
        log.info("Получить все решения конкретного студента");
        return submissionService.findByStudent(studentId);
    }

    @Operation(summary = "Оценить решение")
    @PutMapping("/submission/{id}/grade")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public SubmissionDTO grade(@PathVariable Long id,
                               @RequestBody FeedbackDTO dto) {
        log.info("Оценить решение");
        return submissionService.grade(id, dto);
    }

    @Operation(summary = "Удалить решение")
    @DeleteMapping("/submission/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить решение");
        if (submissionService.getById(id) != null) {
            submissionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
