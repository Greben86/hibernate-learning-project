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
import spring.hibernate.learning.LearningApp.dto.LessonDTO;
import spring.hibernate.learning.LearningApp.service.LessonService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "REST API: Модули")
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "Список занятий")
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDTO> getAll() {
        log.info("Список занятий");
        return lessonService.getAll();
    }

    @Operation(summary = "Занятие по ID")
    @GetMapping("/lesson/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDTO getById(@PathVariable Long id) {
        log.info("Занятие по ID");
        return lessonService.getById(id);
    }

    @Operation(summary = "Добавить занятие")
    @PostMapping("/lesson")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public LessonDTO create(@RequestBody LessonDTO dto) {
        log.info("Добавить занятие");
        return lessonService.addModule(dto);
    }

    @Operation(summary = "Удалить занятие")
    @DeleteMapping("/lesson/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить занятие");
        if (lessonService.getById(id) != null) {
            lessonService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
