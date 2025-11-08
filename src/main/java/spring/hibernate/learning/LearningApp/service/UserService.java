package spring.hibernate.learning.LearningApp.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.dto.SignUpRequest;
import spring.hibernate.learning.LearningApp.dto.UserDTO;
import spring.hibernate.learning.LearningApp.entity.UserEntity;
import spring.hibernate.learning.LearningApp.entity.UserRole;
import spring.hibernate.learning.LearningApp.exception.LogicException;
import spring.hibernate.learning.LearningApp.map.UserMapper;
import spring.hibernate.learning.LearningApp.repository.UserRepository;

import java.util.List;

/**
 * Сервис управления пользователями
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Создание пользователя
     *
     * @param request данные пользователя
     * @return созданный пользователь
     */
    public UserEntity addUser(final SignUpRequest request, final String encodedPassword) {
        if (repository.existsByUsername(request.username())) {
            throw new LogicException("Пользователь с таким именем уже существует");
        }

        final var user = mapper.toEntity(request, encodedPassword);

        return repository.save(user);
    }

    /**
     * Обновление пользователя
     */
    public void saveUser(final UserEntity user) {
        repository.save(user);
    }

    /**
     * Удаление пользователя
     *
     * @param id идентификатор пользователя
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(final Long id) {
        final var user = getCurrentUser();
        if (user.getId().equals(id)) {
            throw new LogicException("Нельзя удалить себя");
        }
        repository.deleteById(id);
    }

    /**
     * Обновление пользователя
     *
     * @return пользователь
     */
    public UserDTO saveUser(@Valid final UserDTO request) {
        final var user = getCurrentUser();
        if (repository.existsByUsername(request.username())) {
            throw new LogicException("Пользователь с таким именем уже существует");
        }

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setRole(UserRole.of(request.role()));
        repository.save(user);

        return mapper.fromEntity(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public UserEntity getByUsername(final String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new LogicException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public UserEntity getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        final var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return getByUsername(username);
    }

    /**
     * Выборка всех пользователей
     *
     * @return список пользователей
     */
    public List<UserDTO> getAllUsers() {
        return repository.findByRoleNot(UserRole.ADMIN).stream()
                .map(mapper::fromEntity)
                .toList();
    }

    /**
     * Выдача прав администратора пользователю
     *
     * @param id идентификатор пользователя
     */
    public void setAdmin(final Long id) {
        final var user = repository.findById(id)
                .orElseThrow(() -> new LogicException("Пользователь не найден"));
        user.setRole(UserRole.ADMIN);
        repository.save(user);
    }
}
