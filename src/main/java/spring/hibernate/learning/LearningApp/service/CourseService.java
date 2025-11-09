package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.dto.CourseReviewDTO;
import spring.hibernate.learning.LearningApp.dto.EnrollmentDTO;
import spring.hibernate.learning.LearningApp.entity.TagEntity;
import spring.hibernate.learning.LearningApp.map.CourseMapper;
import spring.hibernate.learning.LearningApp.repository.CategoryRepository;
import spring.hibernate.learning.LearningApp.repository.CourseRepository;
import spring.hibernate.learning.LearningApp.repository.CourseReviewRepository;
import spring.hibernate.learning.LearningApp.repository.TagRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final TagRepository tagRepository;
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
    public CourseDTO addCourse(CourseDTO dto) {
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseReviewDTO addReview(Long id, CourseReviewDTO dto) {
        final var user = userService.getCurrentUser();
        final var course = courseRepository.getReferenceById(id);
        final var review = mapper.toEntity(dto, course, user);
        courseReviewRepository.save(review);

        return mapper.fromEntity(review);
    }

    public List<CourseReviewDTO> getReviews(Long id) {
        final var course = courseRepository.getReferenceById(id);
        return courseReviewRepository.findByCourse(course).stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<String> addTag(Long id, String tag) {
        final var course = courseRepository.getReferenceById(id);
        final var tags = course.getTags();
        final var tagEntity = tagRepository.findByName(tag)
                .orElse(TagEntity.builder()
                        .name(tag)
                        .build());
        tags.add(tagEntity);
        tagRepository.save(tagEntity);
        courseRepository.save(course);
        return tags.stream()
                .map(TagEntity::getName)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<String> deleteTag(Long id, String tag) {
        final var course = courseRepository.getReferenceById(id);
        final var tags = course.getTags();
        tagRepository.findByName(tag).ifPresent(tags::remove);
        courseRepository.save(course);
        return tags.stream()
                .map(TagEntity::getName)
                .toList();
    }

    public List<String> getTags(Long id) {
        final var course = courseRepository.getReferenceById(id);
        final var tags = course.getTags();
        return tags.stream()
                .map(TagEntity::getName)
                .toList();
    }

    public List<CourseDTO> findByTag(String tag) {
        return courseRepository.findByTagName(tag).stream()
                .map(mapper::fromEntity)
                .toList();
    }
}
