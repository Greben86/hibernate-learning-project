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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.dto.CourseReviewDTO;
import spring.hibernate.learning.LearningApp.dto.EnrollmentDTO;
import spring.hibernate.learning.LearningApp.service.CourseService;
import spring.hibernate.learning.LearningApp.service.EnrollmentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
@Tag(name = "REST API: Управление курсами")
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
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

    @Operation(summary = "Записаться на курс")
    @PostMapping("/enrollment")
    @ResponseStatus(HttpStatus.OK)
    public EnrollmentDTO addEnrollment(@RequestBody @Valid EnrollmentDTO dto) {
        log.info("Записаться на курс");
        return enrollmentService.addEnrollment(dto);
    }

    @Operation(summary = "Список записей на курсы")
    @GetMapping("/enrollments")
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentDTO> getEnrollments() {
        log.info("Список записей на курсы");
        return enrollmentService.getAll();
    }

    @Operation(summary = "Завершить запись на курс")
    @PutMapping("/enrollment/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public EnrollmentDTO completeEnrollment(@PathVariable Long id) {
        log.info("Завершить запись на курс");
        return enrollmentService.complete(id);
    }

    @Operation(summary = "Удалить запись на курс")
    @DeleteMapping("/enrollment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnrollment(@PathVariable Long id) {
        log.info("Удалить запись на курс");
        enrollmentService.delete(id);
    }

    @Operation(summary = "Список тегов курса")
    @GetMapping("/course/{id}/tag")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getTags(@PathVariable Long id) {
        log.info("Список тегов курса");
        return courseService.getTags(id);
    }

    @Operation(summary = "Добавить тег для курса")
    @PutMapping("/course/{id}/tag")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> addTag(@PathVariable Long id, @RequestParam("tag") String tag) {
        log.info("Добавить тег для курса");
        return courseService.addTag(id, tag);
    }

    @Operation(summary = "Убрать тег у курса")
    @DeleteMapping("/course/{id}/tag")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> deleteTag(@PathVariable Long id, @RequestParam("tag") String tag) {
        log.info("Убрать тег у курса");
        return courseService.deleteTag(id, tag);
    }

    @Operation(summary = "Поиск курсов по тегу")
    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDTO> findByTag(@RequestParam("tag") String tag) {
        log.info("Поиск курсов по тегу");
        return courseService.findByTag(tag);
    }

    @Operation(summary = "Удалить курс")
    @DeleteMapping("/course/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(@PathVariable Long id) {
        log.info("Удаление курса");
        courseService.delete(id);
    }
}