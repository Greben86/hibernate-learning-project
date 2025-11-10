package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.dto.CourseReviewDTO;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;
import spring.hibernate.learning.LearningApp.entity.CourseReviewEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "category", expression = "java(null)")
    @Mapping(target = "teacher", expression = "java(null)")
    CourseEntity toEntity(CourseDTO dto);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    CourseReviewEntity toEntity(CourseReviewDTO dto, CourseEntity course, UserEntity author);

    @Mapping(target = "category", expression = "java(entity.getCategory().getName())")
    @Mapping(target = "teacher", expression = "java(entity.getTeacher().getUsername())")
    CourseDTO fromEntity(CourseEntity entity);

    @Mapping(target = "course", expression = "java(entity.getCourse().getTitle())")
    @Mapping(target = "author", expression = "java(entity.getAuthor().getUsername())")
    CourseReviewDTO fromEntity(CourseReviewEntity entity);
}
