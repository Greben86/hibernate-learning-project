package spring.hibernate.learning.LearningApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Пользователь")
public record UserDTO(
        @Schema(description = "Идентификатор пользователя", example = "1")
        long id,

        @Schema(description = "Имя пользователя", example = "Вася")
        @Size(min = 1, max = 50, message = "Имя пользователя должно содержать от 1 до 50 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String username,

        @Schema(description = "Email пользователя", example = "your_email@example.com")
        @Email(message = "Адрес Email должен быть валидным")
        @NotBlank(message = "Поле не может быть пустым")
        String email,

        @Schema(description = "Роль пользователя", example = "Студент")
        @Pattern(regexp = "^Студент|Преподаватель$",
                message = "Роль пользователя может быть только \"Студент\" или \"Преподаватель\"")
        @NotBlank(message = "Роль пользователя не может быть пустой")
        String role) {
}
