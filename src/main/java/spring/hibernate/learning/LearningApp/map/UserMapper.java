package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.SignUpRequest;
import spring.hibernate.learning.LearningApp.dto.UserDTO;
import spring.hibernate.learning.LearningApp.entity.UserEntity;
import spring.hibernate.learning.LearningApp.entity.UserRole;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {UserRole.class, LocalDateTime.class})
public interface UserMapper {

    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "role", expression = "java(UserRole.of(dto.role()))")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    UserEntity from(SignUpRequest dto, String encodedPassword);

    @Mapping(target = "role", expression = "java(entity.getRole().getName())")
    UserDTO to(UserEntity entity);
}