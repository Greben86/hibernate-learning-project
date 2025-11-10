package spring.hibernate.learning.LearningApp.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import spring.hibernate.learning.LearningApp.dto.CategoryDTO;
import spring.hibernate.learning.LearningApp.entity.CategoryEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "id", expression = "java(null)")
    CategoryEntity toEntity(CategoryDTO dto);

    CategoryDTO fromEntity(CategoryEntity entity);
}
