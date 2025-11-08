package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.EnrollmentDTO;
import spring.hibernate.learning.LearningApp.entity.CourseEntity;
import spring.hibernate.learning.LearningApp.entity.EnrollmentEntity;
import spring.hibernate.learning.LearningApp.entity.EnrollmentStatus;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {EnrollmentStatus.class})
public interface EnrollmentMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "enrollDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(EnrollmentStatus.ACTIVE)")
    EnrollmentEntity toEntity(EnrollmentDTO dto, CourseEntity course, UserEntity student);

    @Mapping(target = "student", expression = "java(entity.getStudent().getUsername())")
    @Mapping(target = "course", expression = "java(entity.getCourse().getTitle())")
    @Mapping(target = "status", expression = "java(entity.getStatus().getName())")
    EnrollmentDTO fromEntity(EnrollmentEntity entity);
}
