package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Задание
 */
@Data
@Entity
@Table(name = "assignments")
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignments_generator")
    @SequenceGenerator(name = "assignments_generator", sequenceName = "assignments_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime duedate;

    @Column(columnDefinition = "NUMERIC(5,2)")
    private Double maxScore;

    // Принадлежность уроку
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    // Ответы на задание
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubmissionEntity> submissions;
}
