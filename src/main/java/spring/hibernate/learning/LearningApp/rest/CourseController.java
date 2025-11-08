package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.dto.CourseReviewDTO;
import spring.hibernate.learning.LearningApp.service.CourseService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
@Tag(name = "REST API: Управление курсами")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Список курсов")
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDTO> getAll() {
        log.info("Посмотреть все курсы");
        return courseService.getAll();
    }

    @Operation(summary = "Курс по ID")
    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO getById(@PathVariable Long id) {
        log.info("Курс по ID");
        return courseService.getById(id);
    }

    @Operation(summary = "Зарегистрировать курс")
    @PostMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO addCourse(@RequestBody @Valid CourseDTO dto) {
        log.info("Регистрация курса");
        return courseService.addCourse(dto);
    }

    @Operation(summary = "Добавить отзыв на курс")
    @PostMapping("/course/{id}/review")
    @ResponseStatus(HttpStatus.OK)
    public CourseReviewDTO addReview(@PathVariable Long id, @RequestBody @Valid CourseReviewDTO dto) {
        log.info("Добавить отзыв на курс");
        return courseService.addReview(id, dto);
    }

    @Operation(summary = "Посмотреть отзывы на курс")
    @GetMapping("/course/{id}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseReviewDTO> getReviews(@PathVariable Long id) {
        log.info("Посмотреть отзывы на курс");
        return courseService.getReviews(id);
    }

    @Operation(summary = "Удалить курс")
    @DeleteMapping("/course/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        log.info("Удаление курса");
        courseService.delete(id);
    }
}