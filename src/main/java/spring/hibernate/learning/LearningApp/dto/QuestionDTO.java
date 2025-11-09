package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Вопрос теста")
public record QuestionDTO(
        @Schema(description = "Идентификатор вопроса", example = "1")
        Long id,
        @Schema(description = "Текст вопроса", example = "Вопрос:...")
        @NotBlank(message = "Поле не может быть пустым")
        String text,
        @Schema(description = "Тип ответа", example = "Один")
        @Pattern(regexp = "^Один|Несколько$",
                message = "Тип ответа может быть только \"Один\" или \"Несколько\"")
        @NotBlank(message = "Поле не может быть пустым")
        String type,
        @Schema(description = "Идентификатор теста", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long quizId) {
}
