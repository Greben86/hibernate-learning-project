package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByTitle(String title);

    @Query("SELECT c FROM CourseEntity c WHERE (SELECT t FROM TagEntity t WHERE t.name = ?1) IN (c.tags)")
    List<CourseEntity> findByTagName(String tag);
}
