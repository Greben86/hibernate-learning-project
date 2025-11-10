package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Вопрос теста
 */
@Data
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_generator")
    @SequenceGenerator(name = "questions_generator", sequenceName = "questions_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String text;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    // Привязка к тесту
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

    // Возможные ответы
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerOptionEntity> answerOptions;
}
