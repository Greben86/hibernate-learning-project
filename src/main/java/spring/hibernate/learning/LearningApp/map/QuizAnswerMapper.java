package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.QuizAnswerRequest;
import spring.hibernate.learning.LearningApp.dto.QuizAnswerResponse;
import spring.hibernate.learning.LearningApp.entity.AnswerOptionEntity;
import spring.hibernate.learning.LearningApp.entity.QuestionEntity;
import spring.hibernate.learning.LearningApp.entity.QuizAnswerEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizAnswerMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "option", source = "option")
    QuizAnswerEntity toEntity(QuizAnswerRequest request, UserEntity student, QuestionEntity question, AnswerOptionEntity option);

    @Mapping(target = "student", expression = "java(entity.getStudent().getUsername())")
    @Mapping(target = "question", expression = "java(entity.getQuestion().getText())")
    @Mapping(target = "option", expression = "java(entity.getOption().getText())")
    @Mapping(target = "isCorrect", expression = "java(entity.getOption().getIsCorrect())")
    QuizAnswerResponse fromEntity(QuizAnswerEntity entity);
}
