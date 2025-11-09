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
import spring.hibernate.learning.LearningApp.dto.ModuleDTO;
import spring.hibernate.learning.LearningApp.service.ModuleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "REST API: Модули")
public class ModuleController {
    private final ModuleService moduleService;

    @Operation(summary = "Список модулей")
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<ModuleDTO> getAll() {
        log.info("Список модулей");
        return moduleService.getAll();
    }

    @Operation(summary = "Задание по ID")
    @GetMapping("/module/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ModuleDTO getById(@PathVariable Long id) {
        log.info("Задание по ID");
        return moduleService.getById(id);
    }

    @Operation(summary = "Добавить модуль")
    @PostMapping("/module")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TEACHER')")
    public ModuleDTO create(@RequestBody ModuleDTO dto) {
        log.info("Добавить модуль");
        return moduleService.addModule(dto);
    }

    @Operation(summary = "Удалить модуль")
    @DeleteMapping("/module/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удалить модуль");
        if (moduleService.getById(id) != null) {
            moduleService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
