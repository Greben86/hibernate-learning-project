package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Запись студента на курс")
public record EnrollmentDTO(
        @Schema(description = "Идентификатор записи", example = "1")
        Long id,
        @Schema(description = "Логин студента", example = "Вася")
        String student,
        @Schema(description = "Название курса", example = "Математика")
        @Size(min = 1, max = 255, message = "Название курса должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String course,
        @Schema(description = "Дата и время записи на курс", pattern = "01-01-2026 12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Moscow")
        LocalDateTime enrollDate,
        @Schema(description = "Статус записи", example = "Активная")
        String status) {
}
