package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.CourseReviewEntity;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReviewEntity, Long> {
}
