package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Отзыв о курсе
 */
@Data
@Entity
@Table(name = "course_reviews")
public class CourseReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_reviews_generator")
    @SequenceGenerator(name = "course_reviews_generator", sequenceName = "course_reviews_seq", allocationSize = 1)
    private Long id;

    // Рейтинг курса (например, 1-5 звезд)
    @Column(nullable = false)
    private Integer rating;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String comment;

    // Кто написал отзыв?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private UserEntity author;

    // Какой курс оценён?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
