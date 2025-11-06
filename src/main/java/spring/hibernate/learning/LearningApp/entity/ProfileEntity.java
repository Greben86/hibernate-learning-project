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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешний ключ на пользователя
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private UserEntity user;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String bio;

    @Column
    private String avatarUrl;
}
