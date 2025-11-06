package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Решение задания студентом
 */
@Data
@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Решение связано с заданием
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    // Выполняемый студент
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime submittedAt;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column(columnDefinition = "NUMERIC")
    private Double score;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String feedback;
}
