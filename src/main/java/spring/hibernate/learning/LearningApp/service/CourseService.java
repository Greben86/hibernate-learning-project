package spring.hibernate.learning.LearningApp.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.map.CourseMapper;
import spring.hibernate.learning.LearningApp.repository.CategoryRepository;
import spring.hibernate.learning.LearningApp.repository.CourseRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CourseMapper mapper;

    public List<CourseDTO> getAll() {
        return courseRepository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public CourseDTO getById(Long id) {
        return mapper.fromEntity(courseRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseDTO addCourse(@Valid CourseDTO dto) {
        final var newCourse = mapper.toEntity(dto);
        final var teacher = Objects.requireNonNull(userService.getByUsername(dto.teacher()),
                "Пользователь " + dto.teacher() + " не найден");
        newCourse.setTeacher(teacher);
        final var category = categoryRepository.findByName(dto.category())
                .orElseThrow(() -> new NullPointerException("Категория " + dto.category() + " не найдена"));
        newCourse.setCategory(category);
        return mapper.fromEntity(courseRepository.save(newCourse));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
