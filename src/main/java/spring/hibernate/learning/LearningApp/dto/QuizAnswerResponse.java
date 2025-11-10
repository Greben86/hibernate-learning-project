package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Отображение результата ответа на вопрос теста")
public record QuizAnswerResponse(
        @Schema(description = "Имя пользователя", example = "Вася")
        String student,
        @Schema(description = "Название вопроса", example = "Вопрос")
        @NotNull(message = "Поле не может быть пустым")
        String question,
        @Schema(description = "Название варианта ответа", example = "Вариант 1")
        String option,
        @Schema(description = "Это правильный ответ", example = "true")
        Boolean isCorrect) {
}
