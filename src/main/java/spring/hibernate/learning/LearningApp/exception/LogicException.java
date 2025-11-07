package spring.hibernate.learning.LearningApp.exception;

/**
 * Логическая ошибка
 */
public class LogicException extends RuntimeException {

    public LogicException(String message) {
        super(message);
    }
}
