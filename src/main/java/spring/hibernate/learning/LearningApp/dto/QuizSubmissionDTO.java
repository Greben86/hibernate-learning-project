package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Результат прохождения теста")
public record QuizSubmissionDTO(
        @Schema(description = "Имя пользователя", example = "Вася")
        String student,
        @Schema(description = "Идентификатор теста", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long quizId,
        @Schema(description = "Полученный балл", pattern = "10.0")
        Double score,
        @Schema(description = "Дата и время ответа", pattern = "01-01-2026 12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Moscow")
        LocalDateTime takenAt) {
}
