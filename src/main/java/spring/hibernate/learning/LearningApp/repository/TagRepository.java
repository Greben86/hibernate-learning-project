package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
