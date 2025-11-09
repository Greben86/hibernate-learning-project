package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.ModuleDTO;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;
import spring.hibernate.learning.LearningApp.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "title", expression = "java(dto.title())")
    @Mapping(target = "description", expression = "java(dto.description())")
    ModuleEntity toEntity(ModuleDTO dto, CourseEntity course);

    @Mapping(target = "course", expression = "java(entity.getCourse().getTitle())")
    ModuleDTO fromEntity(ModuleEntity entity);
}
