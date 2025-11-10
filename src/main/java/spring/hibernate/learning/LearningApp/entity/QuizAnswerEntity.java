package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Ответы на вопрос теста
 */
@Data
@Entity
@Table(name = "quiz_answers")
public class QuizAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizzes_generator")
    @SequenceGenerator(name = "quizzes_generator", sequenceName = "quizzes_seq", allocationSize = 1)
    private Long id;

    // Тестируемый студент
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    // Привязка к вопросу
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    // Привязка к варианту ответа
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private AnswerOptionEntity option;
}
