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
 * Тип вопроса
 */
@RequiredArgsConstructor
public enum QuestionType {
    SINGLE_CHOICE("Один"), // Вопрос с одним вариантом ответа
    MULTIPLE_CHOICE("Несколько"); // Вопрос с множественным выбором

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, QuestionType> MAP = Stream.of(values())
            .collect(Collectors.toMap(QuestionType::getName, Function.identity()));

    @JsonCreator
    public static QuestionType of(final String value) {
        return MAP.get(value);
    }
}
