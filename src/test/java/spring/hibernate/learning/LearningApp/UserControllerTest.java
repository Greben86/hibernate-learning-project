package spring.hibernate.learning.LearningApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static spring.hibernate.learning.LearningApp.TestUtils.BEARER_PREFIX;
import static spring.hibernate.learning.LearningApp.TestUtils.HEADER_NAME;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DisplayName("Тестирование API пользователей")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Order(0)
    @DisplayName("Проверка, что MockMvc существует")
    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Order(1)
    @DisplayName("Тест добавления пользователя")
    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign/up")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"Viktor\", \"password\":\"password123\", \"role\": \"Студент\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());

        final var token = TestUtils.signIn(mockMvc, "root", "password123");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/user/2")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Viktor"));
    }

    @Order(2)
    @DisplayName("Тест просмотра списка пользователей")
    @Test
    void getAll() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "root", "password123");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Viktor"));
    }

    @Order(3)
    @DisplayName("Тест добавления прав администратора")
    @Test
    void setAdmin() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "root", "password123");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/user/2/set-admin")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Order(4)
    @DisplayName("Тест удаления пользователя")
    @Test
    void deleteUser() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "root", "password123");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/user/2")
                        .header(HEADER_NAME, BEARER_PREFIX + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(5)
    @DisplayName("Тест ошибки доступа")
    @Test
    void testForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}