package spring.hibernate.learning.LearningApp.service;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import spring.hibernate.learning.LearningApp.entity.EnrollmentEntity;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DisplayName("Тестирование сервиса пользователей")
class UserServiceTest {

    @Autowired
    UserService userService;

    @DisplayName("Проверка, что UserService существует")
    @Test
    void testExist() {
        Assertions.assertNotNull(userService);
    }

    @DisplayName("Проверка, что UserService находит пользователя")
    @Test
    void testGetByUsername() {
        final var root = userService.getByUsername("root");
        Assertions.assertNotNull(root);
    }

    @DisplayName("Проверка, что получим LazyInitializationException при попытке обратиться к Lazy коллекции вне сессии")
    @Test
    void testLazyInitializationException() {
        final var root = userService.getByUsername("root");

        Exception exception = Assertions.assertThrows(LazyInitializationException.class, () -> {
            final var listEnrollments = root.getEnrollments();
            final var ids = listEnrollments.stream()
                    .map(EnrollmentEntity::getId)
                    .toList();
        });

        String expectedMessage = "could not initialize proxy - no Session";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}