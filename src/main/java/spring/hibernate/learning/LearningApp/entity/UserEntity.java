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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Пользователь
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    // Тип перечисления для роли
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role;

    // Пароль хешируется перед сохранением
    @Column(name = "password_hash")
    private String password;

    // Дата регистрации
    @Column(updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    // Ленивая загрузка профилей
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private ProfileEntity profile;

    // Преподаватели ведут курсы
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<CourseEntity> taughtCourses;

    // Студенты участвуют в курсах через EnrollmentEntity
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<EnrollmentEntity> enrollments;

    // Пользователь оставил отзывы о курсах
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<CourseReviewEntity> reviews;

    // Ответы на задания
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<SubmissionEntity> submissions;

    // Оценки за тесты
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<QuizSubmissionEntity> quizSubmissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
