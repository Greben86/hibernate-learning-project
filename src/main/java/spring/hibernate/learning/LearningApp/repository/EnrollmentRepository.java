package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;
import spring.hibernate.learning.LearningApp.entity.EnrollmentEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Optional<EnrollmentEntity> findByCourseAndStudent(CourseEntity course, UserEntity student);
}
