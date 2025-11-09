package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Schema(description = "Ответ на задание")
public record SubmissionDTO(
        @Schema(description = "Идентификатор ответа", example = "1")
        Long id,
        String assignment,
        @Schema(description = "Логин студента", example = "Вася")
        String student,
        @Schema(description = "Дата и время ответа", pattern = "01-01-2026 12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Moscow")
        LocalDateTime submittedAt,
        @Schema(description = "Текст ответа", example = "Ответ...")
        @NotBlank(message = "Поле не может быть пустым")
        String content,
        @Schema(description = "Полученный балл", pattern = "10.0")
        Double score,
        @Schema(description = "Обратная связь", example = "Ответ...")
        String feedback) {
}
