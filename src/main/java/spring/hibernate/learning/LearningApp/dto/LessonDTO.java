package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Занятие курса")
public record LessonDTO(
        @Schema(description = "Идентификатор занятия", example = "1")
        Long id,
        @Schema(description = "Название занятия", example = "Первое занятие")
        @Size(min = 1, max = 255, message = "Название модуля должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String title,
        @Schema(description = "Материал занятия", example = "Первое занятие...")
        @NotBlank(message = "Поле не может быть пустым")
        String content,
        @Schema(description = "Cсылка на видео", example = "http://vk.ru/...")
        @Size(min = 1, max = 255, message = "Cсылка должна содержать от 1 до 255 символов")
        String videoUrl,
        @Schema(description = "Название модуля", example = "Первый модуль")
        @Size(min = 1, max = 255, message = "Название модуля должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String module) {
}
