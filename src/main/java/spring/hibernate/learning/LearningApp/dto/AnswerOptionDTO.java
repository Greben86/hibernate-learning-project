package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Вариант ответа")
public record AnswerOptionDTO(
        @Schema(description = "Идентификатор ответа", example = "1")
        Long id,
        @Schema(description = "Текст ответа", example = "Ответ")
        @Size(min = 1, max = 255, message = "Текст ответа должен содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String text,
        @Schema(description = "Это правильный ответ", example = "true")
        @NotNull(message = "Поле не может быть пустым")
        Boolean isCorrect,
        @Schema(description = "Идентификатор вопроса", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long questionId) {
}
