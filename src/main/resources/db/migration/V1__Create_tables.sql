-- Пользователи (User)
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(20) CHECK(role IN ('STUDENT', 'TEACHER', 'ADMIN')),
    password_hash VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Профили пользователей (Profile)
CREATE TABLE profiles (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    bio TEXT,
    avatar_url VARCHAR(255)
);

-- Категории курсов (Category)
CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

-- Курсы (Course)
CREATE TABLE courses (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category_id BIGINT REFERENCES categories(id),
    teacher_id BIGINT REFERENCES users(id),
    start_date DATE,
    duration INTEGER
);

-- Запись студентов на курсы (Enrollment)
CREATE TABLE enrollments (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    course_id BIGINT REFERENCES courses(id),
    enroll_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK(status IN ('ACTIVE', 'COMPLETED'))
);

-- Модули курса (Module)
CREATE TABLE modules (
    id BIGINT PRIMARY KEY,
    course_id BIGINT REFERENCES courses(id),
    title VARCHAR(255) NOT NULL,
    order_index SMALLINT NOT NULL,
    description TEXT
);

-- Уроки (Lesson)
CREATE TABLE lessons (
    id BIGINT PRIMARY KEY,
    module_id BIGINT REFERENCES modules(id),
    title VARCHAR(255) NOT NULL,
    content TEXT,
    video_url VARCHAR(255)
);

-- Задания (Assignment)
CREATE TABLE assignments (
    id BIGINT PRIMARY KEY,
    lesson_id BIGINT REFERENCES lessons(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duedate TIMESTAMP,
    max_score DECIMAL(5,2)
);

-- Решения заданий студентами (Submission)
CREATE TABLE submissions (
    id BIGINT PRIMARY KEY,
    assignment_id BIGINT REFERENCES assignments(id),
    student_id BIGINT REFERENCES users(id),
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    content TEXT,
    score DECIMAL(5,2),
    feedback TEXT
);

-- Тесты (Quiz)
CREATE TABLE quizzes (
    id BIGINT PRIMARY KEY,
    module_id BIGINT REFERENCES modules(id),
    title VARCHAR(255) NOT NULL,
    timelimit INTEGER
);

-- Вопросы теста (Question)
CREATE TABLE questions (
    id BIGINT PRIMARY KEY,
    quiz_id BIGINT REFERENCES quizzes(id),
    text TEXT NOT NULL,
    type VARCHAR(20) CHECK(type IN ('SINGLE_CHOICE', 'MULTIPLE_CHOICE'))
);

-- Варианты ответов (AnswerOption)
CREATE TABLE answer_options (
    id BIGINT PRIMARY KEY,
    question_id BIGINT REFERENCES questions(id),
    text TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE
);

-- Ответы студента на вопрос теста (QuizAnswer)
CREATE TABLE quiz_answers (
    id BIGINT PRIMARY KEY,
    question_id BIGINT REFERENCES questions(id),
    student_id BIGINT REFERENCES users(id),
    option_id BIGINT REFERENCES answer_options(id)
);

-- Результаты прохождения тестов (QuizSubmission)
CREATE TABLE quiz_submissions (
    id BIGINT PRIMARY KEY,
    quiz_id BIGINT REFERENCES quizzes(id),
    student_id BIGINT REFERENCES users(id),
    score DECIMAL(5,2),
    taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Отзывы о курсе (CourseReview)
CREATE TABLE course_reviews (
    id BIGINT PRIMARY KEY,
    course_id BIGINT REFERENCES courses(id),
    student_id BIGINT REFERENCES users(id),
    rating INTEGER CHECK(rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Теги (Tag)
CREATE TABLE tags (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Связующая таблица курсов и тегов (many-to-many)
CREATE TABLE course_tags (
    course_id BIGINT REFERENCES courses(id),
    tag_id BIGINT REFERENCES tags(id),
    PRIMARY KEY(course_id, tag_id)
);