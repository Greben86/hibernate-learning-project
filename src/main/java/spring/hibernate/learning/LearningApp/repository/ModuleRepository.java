package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.ModuleEntity;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    Optional<ModuleEntity> findByTitle(String title);
}
