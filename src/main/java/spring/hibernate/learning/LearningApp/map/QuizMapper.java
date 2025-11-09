package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.QuizDTO;
import spring.hibernate.learning.LearningApp.entity.ModuleEntity;
import spring.hibernate.learning.LearningApp.entity.QuizEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "title", expression = "java(dto.title())")
    @Mapping(target = "module", source = "module")
    QuizEntity toEntity(QuizDTO dto, ModuleEntity module);

    @Mapping(target = "moduleId", expression = "java(entity.getModule().getId())")
    QuizDTO fromEntity(QuizEntity entity);
}
