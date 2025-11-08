package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.service.CourseService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
@Tag(name = "REST API: Управление курсами")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Список бронирований пользователя")
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDTO> getAll() {
        log.info("Посмотреть все бронирования пользователя");
        return courseService.getAll();
    }

    @Operation(summary = "Бронирование пользователя по ID")
    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO getById(@PathVariable Long id) {
        log.info("Запрос бронирования по ID");
        return courseService.getById(id);
    }

    @Operation(summary = "Зарегистрировать бронирование")
    @PostMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO addBooking(@RequestBody CourseDTO booking) {
        log.info("Регистрация бронирования");
        return courseService.addCourse(booking);
    }

    @Operation(summary = "Удалить бронирование")
    @DeleteMapping("/course/{id}")
    public ResponseEntity<Void> releaseBooking(@PathVariable Long id) {
        log.info("Удаление бронирования");
        if (courseService.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}