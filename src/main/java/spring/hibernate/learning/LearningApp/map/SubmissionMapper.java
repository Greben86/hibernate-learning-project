package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.SubmissionDTO;
import spring.hibernate.learning.LearningApp.entity.AssignmentEntity;
import spring.hibernate.learning.LearningApp.entity.SubmissionEntity;
import spring.hibernate.learning.LearningApp.entity.UserEntity;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class})
public interface SubmissionMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "assignment", source = "assignment")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "submittedAt", expression = "java(LocalDateTime.now())")
    SubmissionEntity toEntity(SubmissionDTO dto, AssignmentEntity assignment, UserEntity student);

    @Mapping(target = "assignment", expression = "java(entity.getAssignment().getTitle())")
    @Mapping(target = "student", expression = "java(entity.getStudent().getUsername())")
    SubmissionDTO fromEntity(SubmissionEntity entity);
}
