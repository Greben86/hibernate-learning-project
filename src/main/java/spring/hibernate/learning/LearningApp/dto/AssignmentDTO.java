package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Задание")
public record AssignmentDTO(
        @Schema(description = "Идентификатор задания", example = "1")
        Long id,
        @Schema(description = "Название задания", example = "Задание")
        @Size(min = 1, max = 255, message = "Название задания должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String title,
        @Schema(description = "Текст задания", example = "Задание...")
        @NotBlank(message = "Поле не может быть пустым")
        String description,
        @Schema(description = "Дедлайн", pattern = "01-01-2026 12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Moscow")
        @NotNull(message = "Поле не может быть пустым")
        LocalDateTime duedate,
        @Schema(description = "Максимальный балл", pattern = "10.0")
        @NotNull(message = "Поле не может быть пустым")
        Double maxScore,
        @Schema(description = "Название занятия", example = "Первое занятие")
        @Size(min = 1, max = 255, message = "Название модуля должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String lesson) {
}
