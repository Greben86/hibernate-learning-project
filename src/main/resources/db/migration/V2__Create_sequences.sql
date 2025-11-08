-- Пользователи (User)
CREATE SEQUENCE users_seq START WITH 1;

-- Профили пользователей (Profile)
CREATE SEQUENCE profiles_seq START WITH 1;

-- Категории курсов (Category)
CREATE SEQUENCE categories_seq START WITH 1;

-- Курсы (Course)
CREATE SEQUENCE courses_seq START WITH 1;

-- Запись студентов на курсы (Enrollment)
CREATE SEQUENCE enrollments_seq START WITH 1;

-- Модули курса (Module)
CREATE SEQUENCE modules_seq START WITH 1;

-- Уроки (Lesson)
CREATE SEQUENCE lessons_seq START WITH 1;

-- Задания (Assignment)
CREATE SEQUENCE assignments_seq START WITH 1;

-- Решения заданий студентами (Submission)
CREATE SEQUENCE submissions_seq START WITH 1;

-- Тесты (Quiz)
CREATE SEQUENCE quizzes_seq START WITH 1;

-- Вопросы теста (Question)
CREATE SEQUENCE questions_seq START WITH 1;

-- Варианты ответов (AnswerOption)
CREATE SEQUENCE answer_options_seq START WITH 1;

-- Результаты прохождения тестов (QuizSubmission)
CREATE SEQUENCE quiz_submissions_seq START WITH 1;

-- Отзывы о курсе (CourseReview)
CREATE SEQUENCE course_reviews_seq START WITH 1;

-- Теги (Tag)
CREATE SEQUENCE tags_seq START WITH 1;

-- Связующая таблица курсов и тегов (many-to-many)
CREATE SEQUENCE course_tags_seq START WITH 1;