package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Профиль пользователя
 */
@Data
@Entity
@Table(name = "profiles")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_generator")
    @SequenceGenerator(name = "profiles_generator", sequenceName = "profiles_seq", allocationSize = 1)
    private Long id;

    // Внешний ключ на пользователя
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String bio;

    @Column
    private String avatarUrl;
}
