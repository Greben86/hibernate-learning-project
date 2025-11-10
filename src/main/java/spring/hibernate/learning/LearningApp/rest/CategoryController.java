package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.CategoryDTO;
import spring.hibernate.learning.LearningApp.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
@Tag(name = "REST API: Управление категориями")
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "Список категорий")
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAll() {
        log.info("Посмотреть все категории");
        return service.getAll();
    }

    @Operation(summary = "Категория по ID")
    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getById(@PathVariable Long id) {
        log.info("Категория по ID");
        return service.getById(id);
    }

    @Operation(summary = "Зарегистрировать категорию")
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public CategoryDTO addCategory(@RequestBody @Valid CategoryDTO dto) {
        log.info("Регистрация категории");
        return service.addCategory(dto);
    }

    @Operation(summary = "Удалить категорию")
    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(@PathVariable Long id) {
        log.info("Удаление категории");
        service.delete(id);
    }
}