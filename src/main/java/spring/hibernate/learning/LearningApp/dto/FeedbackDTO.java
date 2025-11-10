package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Обратная связь для ответ на задание")
public record FeedbackDTO(
        @Schema(description = "Полученный балл", pattern = "10.0")
        @NotNull(message = "Поле не может быть пустым")
        Double score,
        @Schema(description = "Обратная связь", example = "Ответ...")
        @NotNull(message = "Поле не может быть пустым")
        String feedback) {
}
