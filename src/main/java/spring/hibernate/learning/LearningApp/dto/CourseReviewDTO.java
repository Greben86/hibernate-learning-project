package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Отзыв о курсе")
public record CourseReviewDTO(
        @Schema(description = "Идентификатор курса", example = "1")
        Long id,
        @Schema(description = "Рейтинг курса", example = "5")
        @Min(value = 1, message = "Длинна кода не может быть меньше 1")
        @Max(value = 5, message = "Длинна кода не может быть больше 5")
        @NotNull(message = "Поле не может быть пустым")
        Integer rating,
        @Schema(description = "Название категории", example = "1")
        @Size(min = 1, max = 255, message = "Название категории должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String comment,
        @Schema(description = "Логин пользователя", example = "1")
        @Size(min = 1, max = 255, message = "Логин пользователя должен содержать от 1 до 255 символов")
        String author,
        @Schema(description = "Название курса", example = "Математика")
        @Size(min = 1, max = 255, message = "Название курса должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String course,
        @Schema(description = "Дата и время отзыва на курс", pattern = "01-01-2026 12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Moscow")
        LocalDateTime createdAt) {
}
