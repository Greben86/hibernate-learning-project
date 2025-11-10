package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Тест
 */
@Data
@Entity
@Table(name = "quizzes")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizzes_generator")
    @SequenceGenerator(name = "quizzes_generator", sequenceName = "quizzes_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "timelimit")
    private Integer timeLimit;

    // Тест привязан к модулю
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    // Вопросы теста
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    // Результаты тестирования
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuizSubmissionEntity> quizSubmissions;
}
