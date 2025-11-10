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
import spring.hibernate.learning.LearningApp.dto.AssignmentDTO;
import spring.hibernate.learning.LearningApp.service.AssignmentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Tag(name = "REST API: Задания")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @Operation(summary = "Список заданий")
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<AssignmentDTO> getAll() {
        log.info("Список заданий");
        return assignmentService.getAll();
    }

    @Operation(summary = "Задание по ID")
    @GetMapping("/assignment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssignmentDTO getById(@PathVariable Long id) {
        log.info("Задание по ID");
        return assignmentService.getById(id);
    }

    @Operation(summary = "Добавить задание")
    @PostMapping("/assignment")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public AssignmentDTO create(@RequestBody AssignmentDTO dto) {
        log.info("Добавить задание");
        return assignmentService.addAssignment(dto);
    }

    @Operation(summary = "Удалить задание")
    @DeleteMapping("/assignment/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить задание");
        if (assignmentService.getById(id) != null) {
            assignmentService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
