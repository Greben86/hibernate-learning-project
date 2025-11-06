package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Курс
 */
@Data
@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "interval")
    private Duration duration;

    // Курс принадлежит категории
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    // Автор курса (преподаватель)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    // Коллекция модулей курса
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ModuleEntity> modules;

    // Учащиеся курса через EnrollmentEntity
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<EnrollmentEntity> enrollments;

    // Список отзывов о курсе
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseReviewEntity> reviews;

    // Теги курса
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;
}
