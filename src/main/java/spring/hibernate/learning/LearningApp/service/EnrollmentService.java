package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.EnrollmentDTO;
import spring.hibernate.learning.LearningApp.entity.EnrollmentStatus;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.EnrollmentMapper;
import spring.hibernate.learning.LearningApp.repository.CourseRepository;
import spring.hibernate.learning.LearningApp.repository.EnrollmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper mapper;

    public List<EnrollmentDTO> getAll() {
        final var user = userService.getCurrentUser();
        return user.getEnrollments().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public EnrollmentDTO getById(Long id) {
        return mapper.fromEntity(enrollmentRepository.getReferenceById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EnrollmentDTO addEnrollment(EnrollmentDTO dto) {
        final var user = userService.getCurrentUser();
        final var course = courseRepository.findByTitle(dto.course())
                .orElseThrow(() -> new LogicException("Курс "+dto.course()+" не найден"));
        final var found = enrollmentRepository.findByCourseAndStudent(course, user);
        if (found.isPresent() && EnrollmentStatus.ACTIVE.equals(found.get().getStatus())) {
            throw new LogicException("На курс "+dto.course()+" у студента "+user.getUsername()+" активная запись уже есть");
        }
        final var entity = mapper.toEntity(dto, course, user);
        return mapper.fromEntity(enrollmentRepository.save(entity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EnrollmentDTO complete(Long id) {
        final var enrollment = enrollmentRepository.getReferenceById(id);
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        return mapper.fromEntity(enrollmentRepository.save(enrollment));
    }
}
