package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.AssignmentDTO;
import spring.hibernate.learning.LearningApp.entity.AssignmentEntity;
import spring.hibernate.learning.LearningApp.entity.LessonEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssignmentMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "lesson", source = "lesson")
    @Mapping(target = "title", expression = "java(dto.title())")
    AssignmentEntity toEntity(AssignmentDTO dto, LessonEntity lesson);

    @Mapping(target = "lesson", expression = "java(entity.getLesson().getTitle())")
    AssignmentDTO fromEntity(AssignmentEntity entity);
}
