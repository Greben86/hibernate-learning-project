package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.QuizSubmissionDTO;
import spring.hibernate.learning.LearningApp.entity.QuizEntity;
import spring.hibernate.learning.LearningApp.entity.QuizSubmissionEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = LocalDateTime.class)
public interface QuizSubmissionMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "quiz", source = "quiz")
    @Mapping(target = "takenAt", expression = "java(LocalDateTime.now())")
    QuizSubmissionEntity toEntity(QuizSubmissionDTO dto, QuizEntity quiz, UserEntity student);

    @Mapping(target = "student", expression = "java(entity.getStudent().getUsername())")
    @Mapping(target = "quizId", expression = "java(entity.getQuiz().getId())")
    QuizSubmissionDTO fromEntity(QuizSubmissionEntity entity);
}
