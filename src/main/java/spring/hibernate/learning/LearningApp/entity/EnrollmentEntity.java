package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись студентов на курсы
 */
@Data
@Entity
@Table(name = "enrollments")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollments_generator")
    @SequenceGenerator(name = "enrollments_generator", sequenceName = "enrollments_seq", allocationSize = 1)
    private Long id;

    // Студент записался на курс
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity student;

    // Привязка к курсу
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime enrollDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
