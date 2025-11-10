package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.LessonDTO;
import spring.hibernate.learning.LearningApp.entity.LessonEntity;
import spring.hibernate.learning.LearningApp.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "module", source = "module")
    @Mapping(target = "title", expression = "java(dto.title())")
    LessonEntity toEntity(LessonDTO dto, ModuleEntity module);

    @Mapping(target = "module", expression = "java(entity.getModule().getTitle())")
    LessonDTO fromEntity(LessonEntity entity);
}
