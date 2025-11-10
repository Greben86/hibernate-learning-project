package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.AnswerOptionDTO;
import spring.hibernate.learning.LearningApp.entity.AnswerOptionEntity;
import spring.hibernate.learning.LearningApp.entity.QuestionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerOptionMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "text", expression = "java(dto.text())")
    @Mapping(target = "question", source = "question")
    AnswerOptionEntity toEntity(AnswerOptionDTO dto, QuestionEntity question);

    @Mapping(target = "questionId", expression = "java(entity.getQuestion().getId())")
    AnswerOptionDTO fromEntity(AnswerOptionEntity entity);
}
