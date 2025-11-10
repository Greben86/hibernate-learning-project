package spring.hibernate.learning.LearningApp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Статус записи на курс
 */
@RequiredArgsConstructor
public enum EnrollmentStatus {
    ACTIVE("Активная"), // Активная
    COMPLETED("Завершена"); // Завершена

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, EnrollmentStatus> MAP = Stream.of(values())
            .collect(Collectors.toMap(EnrollmentStatus::getName, Function.identity()));

    @JsonCreator
    public static EnrollmentStatus of(final String value) {
        return MAP.get(value);
    }
}
