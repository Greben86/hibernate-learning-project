package spring.hibernate.learning.LearningApp.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hibernate.learning.LearningApp.dto.CourseDTO;
import spring.hibernate.learning.LearningApp.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<CourseDTO> getAll() {
        return null;
    }

    public CourseDTO getById(Long id) {
        return null;
    }

    public CourseDTO addCourse(@Valid CourseDTO booking) {
        return null;
    }

    public boolean delete(Long id) {
        return false;
    }
}
