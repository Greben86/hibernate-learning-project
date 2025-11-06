package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Модуль курса
 */
@Data
@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "int2")
    private Integer orderIndex;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    // Каждый модуль принадлежит одному курсу
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    // Модуль содержит уроки
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    // Модуль может содержать викторину
    @OneToOne(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Quiz quiz;
}
