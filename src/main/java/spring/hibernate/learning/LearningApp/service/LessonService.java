package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.LessonDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.LessonMapper;
import spring.hibernate.learning.LearningApp.repository.LessonRepository;
import spring.hibernate.learning.LearningApp.repository.ModuleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final LessonMapper mapper;

    public List<LessonDTO> getAll() {
        return lessonRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public LessonDTO getById(Long id) {
        return mapper.fromEntity(lessonRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LessonDTO addModule(LessonDTO dto) {
        final var module = moduleRepository.findByTitle(dto.module())
                .orElseThrow(() -> new LogicException("Модуль "+dto.module()+" не найден"));
        final var newModule = mapper.toEntity(dto, module);
        return mapper.fromEntity(lessonRepository.save(newModule));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}
