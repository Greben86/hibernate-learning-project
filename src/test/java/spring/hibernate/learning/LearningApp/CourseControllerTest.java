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
@DisplayName("Тестирование API курсов")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Order(0)
    @DisplayName("Проверка, что MockMvc существует")
    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Order(1)
    @DisplayName("Тест добавления преподавателя")
    @Test
    void testAddTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign/up")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "username": "Teacher",
                                  "password": "password123",
                                  "role": "Преподаватель"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Order(2)
    @DisplayName("Тест добавления категории")
    @Test
    void save() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories/category")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "name": "Первая категория",
                                  "description": "Первая категория..."
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Первая категория"));
    }

    @Order(3)
    @DisplayName("Тест добавления курса")
    @Test
    void getById() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses/course")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "title": "Математика",
                                  "description": "Курс математики...",
                                  "startDate": "01-01-2026",
                                  "duration": 100,
                                  "category": "Первая категория",
                                  "teacher": "root"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Математика"));
    }

    @Order(4)
    @DisplayName("Тест получения всех курсов")
    @Test
    void getAll() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Математика"));
    }

    @Order(5)
    @DisplayName("Тест добавления модуля")
    @Test
    void testAddModule() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/modules/module")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "title": "Первый модуль",
                                  "orderIndex": 1,
                                  "description": "Первый модуль...",
                                  "course": "Математика"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Первый модуль"));
    }

    @Order(6)
    @DisplayName("Тест добавления занятия")
    @Test
    void testAddLesson() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lessons/lesson")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "title": "Первое занятие",
                                  "content": "Первое занятие...",
                                  "videoUrl": "http://vk.ru/...",
                                  "module": "Первый модуль"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Первое занятие"));
    }

    @Order(7)
    @DisplayName("Тест добавления задания")
    @Test
    void testAddAssigment() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/assignments/assignment")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "title": "Задание",
                                  "description": "Задание...",
                                  "duedate": "01-01-2026 12:00:00",
                                  "maxScore": 10.0,
                                  "lesson": "Первое занятие"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Задание"));
    }

    @Order(8)
    @DisplayName("Тест добавления решения задания")
    @Test
    void testAddSubmission() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/submissions/submission")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "assignment": "Задание",
                                  "student": "Вася",
                                  "submittedAt": "01-01-2026 12:00:00",
                                  "content": "Ответ..."
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignment").value("Задание"));
    }

    @Order(9)
    @DisplayName("Тест добавления обратной связи для решения")
    @Test
    void testAddFeedback() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/submissions/submission/1/grade")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "score": 0.1,
                                  "feedback": "Ответ..."
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignment").value("Задание"));
    }

    @Order(10)
    @DisplayName("Тест добавления теста")
    @Test
    void testAddQuiz() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizes/quiz")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "title": "Квиз",
                                  "timeLimit": 45,
                                  "moduleId": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Квиз"));
    }

    @Order(10)
    @DisplayName("Тест добавления вопроса теста")
    @Test
    void testAddQuestion() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/questions/question")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "text": "Вопрос:...",
                                  "type": "Один",
                                  "quizId": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Вопрос:..."));
    }

    @Order(11)
    @DisplayName("Тест добавления варианта ответа")
    @Test
    void testAddAnswerOption() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/questions/answer")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "text": "Ответ",
                                  "isCorrect": true,
                                  "questionId": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Ответ"));
    }

    @Order(12)
    @DisplayName("Тест отправки варианта ответа")
    @Test
    void testSendQuizAnswer() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/questions/student-answer")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "questionId": 1,
                                  "optionId": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isCorrect").value("true"));
    }

    @Order(13)
    @DisplayName("Тест просмотра ответов студента")
    @Test
    void testShowQuizAnswer() throws Exception {
        final var token = TestUtils.signIn(mockMvc, "Teacher", "password123");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/question/1/student-answers")
                        .header(HEADER_NAME, BEARER_PREFIX + token)
                        .param("student", "Teacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isCorrect").value("true"));
    }

    @Order(12)
    @DisplayName("Тест ошибки доступа")
    @Test
    void testForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}