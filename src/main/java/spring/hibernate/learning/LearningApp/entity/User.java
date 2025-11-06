package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Пользователь
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    // Тип перечисления для роли
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "USER_ROLE DEFAULT 'STUDENT'")
    private UserRole role;

    // Пароль хешируется перед сохранением
    @Column
    private String passwordHash;

    // Дата регистрации
    @Column(updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    // Ленивая загрузка профилей
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Profile profile;

    // Преподаватели ведут курсы
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> taughtCourses;

    // Студенты участвуют в курсах через Enrollment
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    // Пользователь оставил отзывы о курсах
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<CourseReview> reviews;

    // Ответы на задания
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Submission> submissions;

    // Оценки за тесты
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<QuizSubmission> quizSubmissions;
}
