package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Результат прохождения теста
 */
@Data
@Entity
@Table(name = "quiz_submissions")
public class QuizSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Тестируемый студент
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    // Сданный тест
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(columnDefinition = "NUMERIC")
    private Double score;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime takenAt;
}
