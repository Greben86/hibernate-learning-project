package spring.hibernate.learning.LearningApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Уроки
 */
@Data
@Entity
@Table(name = "lessons")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column
    private String videoUrl;

    // Урок входит в модуль
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    // Каждое занятие может включать задания
    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AssignmentEntity> assignments;
}
