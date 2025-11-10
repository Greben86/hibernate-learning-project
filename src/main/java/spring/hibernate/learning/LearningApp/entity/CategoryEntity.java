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
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_generator")
    @SequenceGenerator(name = "categories_generator", sequenceName = "categories_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    // Обратная сторона отношения: список курсов в данной категории
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CourseEntity> courses;
}
