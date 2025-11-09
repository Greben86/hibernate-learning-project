package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.ModuleDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.ModuleMapper;
import spring.hibernate.learning.LearningApp.repository.CourseRepository;
import spring.hibernate.learning.LearningApp.repository.ModuleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleMapper mapper;

    public List<ModuleDTO> getAll() {
        return moduleRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public ModuleDTO getById(Long id) {
        return mapper.fromEntity(moduleRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ModuleDTO addModule(ModuleDTO dto) {
        final var course = courseRepository.findByTitle(dto.course())
                .orElseThrow(() -> new LogicException("Курс "+dto.course()+" не найден"));
        final var newModule = mapper.toEntity(dto, course);
        return mapper.fromEntity(moduleRepository.save(newModule));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }
}
