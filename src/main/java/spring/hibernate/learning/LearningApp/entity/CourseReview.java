package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Отзыв о курсе
 */
@Data
@Entity
@Table(name = "course_reviews")
public class CourseReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Рейтинг курса (например, 1-5 звезд)
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String comment;

    // Кто написал отзыв?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User author;

    // Какой курс оценён?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
