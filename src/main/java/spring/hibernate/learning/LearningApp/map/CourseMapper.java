package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    @Mapping(target = "category", expression = "java(null)")
    @Mapping(target = "teacher", expression = "java(null)")
    CourseEntity toEntity(CourseDTO dto);

    @Mapping(target = "category", expression = "java(entity.getCategory().getId())")
    @Mapping(target = "teacher", expression = "java(entity.getTeacher().getId())")
    CourseDTO fromEntity(CourseEntity entity);
}
