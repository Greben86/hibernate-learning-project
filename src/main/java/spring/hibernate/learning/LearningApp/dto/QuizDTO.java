package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Тест")
public record QuizDTO(
        @Schema(description = "Идентификатор теста", example = "1")
        Long id,

        @Schema(description = "Название теста", example = "Квиз")
        @Size(min = 1, max = 255, message = "Название теста должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String title,

        @Schema(description = "Лимит времени", example = "45")
        @Min(value = 0, message = "Лимит времени не может быть меньше 0")
        @NotNull(message = "Поле не может быть пустым")
        Integer timeLimit,

        @Schema(description = "Идентификатор модуля", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long moduleId) {
}
