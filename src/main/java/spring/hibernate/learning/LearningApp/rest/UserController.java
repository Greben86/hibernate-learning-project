package spring.hibernate.learning.LearningApp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.hibernate.learning.LearningApp.dto.ProfileDTO;
import spring.hibernate.learning.LearningApp.dto.UserDTO;
import spring.hibernate.learning.LearningApp.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
@Tag(name = "REST API: Пользователь")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Редактирование пользователя")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/user/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO editUser(@RequestBody @Valid UserDTO dto) {
        log.info("Редактирование пользователя");
        return userService.saveUser(dto);
    }

    @Operation(summary = "Список всех пользователей, кроме администраторов")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"", "/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        log.info("Список всех пользователей, кроме администраторов");
        return userService.getAllUsers();
    }

    @Operation(summary = "Удаление пользователя")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/user/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Удаление пользователя");
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}/set-admin")
    @Operation(summary = "Добавить роль ADMIN пользователю")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> getAdmin(@PathVariable("id") Long id) {
        log.info("Добавление роли ADMIN пользователю");
        userService.setAdmin(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Информация о пользователе")
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getById(@PathVariable Long id) {
        log.info("Информация о пользователе");
        return userService.getById(id);
    }

    @Operation(summary = "Редактирование профиля")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/user/profile",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProfileDTO editProfile(@RequestBody @Valid ProfileDTO dto) {
        log.info("Редактирование профиля");
        return userService.updateProfile(dto);
    }

    @Operation(summary = "Просмотр профиля")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProfileDTO getProfile() {
        log.info("Просмотр профиля");
        return userService.getProfile();
    }
}
