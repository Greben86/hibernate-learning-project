package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Ответ на вопрос теста")
public record QuizAnswerRequest(
        @Schema(description = "Идентификатор вопроса", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long questionId,
        @Schema(description = "Идентификатор варианта ответа", example = "1")
        @NotNull(message = "Поле не может быть пустым")
        Long optionId) {
}
