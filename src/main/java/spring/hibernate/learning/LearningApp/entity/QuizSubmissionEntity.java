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
public class QuizSubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_submissions_generator")
    @SequenceGenerator(name = "quiz_submissions_generator", sequenceName = "quiz_submissions_seq", allocationSize = 1)
    private Long id;

    // Тестируемый студент
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    // Сданный тест
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

    @Column(columnDefinition = "NUMERIC")
    private Double score;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime takenAt;
}
