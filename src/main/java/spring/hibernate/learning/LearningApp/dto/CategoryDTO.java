package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Категория")
public record CategoryDTO(
        @Schema(description = "Идентификатор категории", example = "1")
        Long id,
        @Schema(description = "Название категории", example = "Первая категория")
        @Size(min = 1, max = 255, message = "Название категории должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String name,
        @Schema(description = "Описание категории", example = "Первая категория...")
        @Size(min = 1, message = "Название категории должно содержать от 1 символа")
        @NotBlank(message = "Поле не может быть пустым")
        String description) {
}
