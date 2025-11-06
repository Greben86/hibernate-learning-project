package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.EnrollmentEntity;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
}
