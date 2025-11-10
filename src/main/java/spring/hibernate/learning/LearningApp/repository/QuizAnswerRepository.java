package spring.hibernate.learning.LearningApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.learning.LearningApp.entity.QuestionEntity;
import spring.hibernate.learning.LearningApp.entity.QuizAnswerEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswerEntity, Long> {
    List<QuizAnswerEntity> findByStudentAndQuestion(UserEntity student, QuestionEntity question);
}
