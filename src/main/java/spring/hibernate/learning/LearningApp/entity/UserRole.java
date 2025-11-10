package spring.hibernate.learning.LearningApp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Перечисление для ролей
@RequiredArgsConstructor
public enum UserRole {
    STUDENT("Студент"), // Студент
    TEACHER("Преподаватель"), // Преподаватель
    ADMIN("Администратор");  // Администратор

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, UserRole> MAP = Stream.of(values())
            .collect(Collectors.toMap(UserRole::getName, Function.identity()));

    @JsonCreator
    public static UserRole of(final String value) {
        return MAP.get(value);
    }
}
