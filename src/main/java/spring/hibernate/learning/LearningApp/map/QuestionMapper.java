package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.QuestionDTO;
import spring.hibernate.learning.LearningApp.entity.QuestionEntity;
import spring.hibernate.learning.LearningApp.entity.QuizEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "quiz", source = "quiz")
    QuestionEntity toEntity(QuestionDTO dto, QuizEntity quiz);

    @Mapping(target = "quizId", expression = "java(entity.getQuiz().getId())")
    QuestionDTO fromEntity(QuestionEntity entity);
}
