package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Вариант ответа
 */
@Data
@Entity
@Table(name = "answer_options")
public class AnswerOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_options_generator")
    @SequenceGenerator(name = "answer_options_generator", sequenceName = "answer_options_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String text;

    @Column(nullable = false)
    private Boolean isCorrect;

    // Привязка к вопросу
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
