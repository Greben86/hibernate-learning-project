package spring.hibernate.learning.LearningApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDate;

@Schema(description = "Курс")
public record CourseDTO(
        @Schema(description = "Идентификатор курса", example = "1")
        Long id,
        @Schema(description = "Название курса", example = "Математика")
        @Size(min = 1, max = 255, message = "Название курса должно содержать от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String title,
        @Schema(description = "Описание курса", example = "Курс математики...")
        String description,
        @Schema(description = "Дата начала курса", pattern = "dd-MM-yyyy")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="Europe/Moscow")
        @NotNull(message = "Дата начала курса не может быть пустым")
        LocalDate startDate,
        @Schema(description = "Длительность курса", pattern = "10")
        @NotNull(message = "Длительность курса не может быть пустой")
        Integer duration,
        @Schema(description = "Название категории", example = "1")
        @Size(min = 1, max = 255, message = "Название категории должно содержать от 1 до 255 символов")
        @NotBlank(message = "Название категории не может быть пустым")
        String category,
        @Schema(description = "Логин преподавателя", example = "1")
        @Size(min = 1, max = 255, message = "Логин преподавателя должен содержать от 1 до 255 символов")
        @NotBlank(message = "Логин преподавателя не может быть пустым")
        String teacher) {
}
