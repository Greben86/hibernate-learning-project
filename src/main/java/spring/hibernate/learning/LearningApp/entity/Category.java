package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Категория курсов
 */
@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Возможно дополнительные поля, например описание
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    // Обратная сторона отношения: список курсов в данной категории
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Course> courses;
}
