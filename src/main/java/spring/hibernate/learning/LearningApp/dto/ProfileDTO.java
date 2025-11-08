package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Профиль пользователя")
public record ProfileDTO(
        @Schema(description = "Email пользователя", example = "your_email@example.com")
        @Email(message = "Адрес Email должен быть валидным")
        @NotBlank(message = "Поле не может быть пустым")
        String email,

        @Schema(description = "Описание пользователя", example = "Обычный пользователь...")
        String bio,

        @Schema(description = "Cсылка на аватар пользователя", example = "http://vk.ru/...")
        @Size(min = 1, max = 255, message = "Cсылка должна содержать от 1 до 255 символов")
        String avatarUrl) {
}
