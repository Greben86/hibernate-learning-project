package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.CategoryDTO;
import spring.hibernate.learning.LearningApp.map.CategoryMapper;
import spring.hibernate.learning.LearningApp.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public CategoryDTO getById(Long id) {
        return mapper.fromEntity(categoryRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CategoryDTO addCategory(CategoryDTO dto) {
        final var newCategory = mapper.toEntity(dto);
        return mapper.fromEntity(categoryRepository.save(newCategory));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
