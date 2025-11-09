package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.AssignmentDTO;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.AssignmentMapper;
import spring.hibernate.learning.LearningApp.repository.AssignmentRepository;
import spring.hibernate.learning.LearningApp.repository.LessonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentMapper mapper;

    public List<AssignmentDTO> getAll() {
        return assignmentRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public AssignmentDTO getById(Long id) {
        return mapper.fromEntity(assignmentRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AssignmentDTO addAssignment(AssignmentDTO dto) {
        final var lesson = lessonRepository.findByTitle(dto.lesson())
                .orElseThrow(() -> new LogicException("Занятие "+dto.lesson()+" не найдено"));
        final var entity = mapper.toEntity(dto, lesson);
        return mapper.fromEntity(assignmentRepository.save(entity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}
