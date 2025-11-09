package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Модуль курса")
public record ModuleDTO(
        @Schema(description = "Идентификатор модуля", example = "1")
        Long id,
        @Schema(description = "Название модуля", example = "Первый модуль")
        @Size(min = 1, max = 255, message = "Название модуля должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String title,
        @Schema(description = "Порядковый номер модуля", example = "1")
        @Min(value = 0, message = "Порядковый номер модуля не может быть меньше 0")
        @NotNull(message = "Поле не может быть пустым")
        Integer orderIndex,
        @Schema(description = "Описание модуля", example = "Первый модуль...")
        @NotBlank(message = "Поле не может быть пустым")
        String description,
        @Schema(description = "Название курса", example = "Математика")
        @Size(min = 1, max = 255, message = "Название курса должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String course) {
}
