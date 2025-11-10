package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;
import spring.hibernate.learning.LearningApp.entity.CourseReviewEntity;

import java.util.List;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReviewEntity, Long> {
    List<CourseReviewEntity> findByCourse(CourseEntity course);
}
