-- Создание двух категорий
INSERT INTO categories (id, name) VALUES
(nextval('categories_seq'), 'Естественные науки'),
(nextval('categories_seq'), 'Информационные технологии');

-- Добавляем три курса
INSERT INTO courses (id, title, description, category_id, teacher_id) VALUES
(nextval('courses_seq'), 'Математика', 'Курс математики для начинающих.', (SELECT id FROM categories WHERE name = 'Естественные науки'), (SELECT id FROM users WHERE name = 'teacher1')),
(nextval('courses_seq'), 'Информатика', 'Основы информатики и компьютерных наук.', (SELECT id FROM categories WHERE name = 'Информационные технологии'), (SELECT id FROM users WHERE name = 'teacher1')),
(nextval('courses_seq'), 'Программирование', 'Изучаем основы программирования.', (SELECT id FROM categories WHERE name = 'Информационные технологии'), (SELECT id FROM users WHERE name = 'teacher1'));

-- Создаем два модуля для курса "Математика"
INSERT INTO modules (id, course_id, title, order_index, description) VALUES
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Математика'), 'Алгебра', 1, 'Основные понятия алгебры.'),
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Математика'), 'Геометрия', 2, 'Основные геометрические фигуры.');

-- Создаем два модуля для курса "Информатика"
INSERT INTO modules (id, course_id, title, order_index, description) VALUES
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Информатика'), 'Компьютерные сети', 1, 'Устройство сетей.'),
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Информатика'), 'Архитектура компьютеров', 2, 'Организация вычислительных машин.');

-- Создаем два модуля для курса "Программирование"
INSERT INTO modules (id, course_id, title, order_index, description) VALUES
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Программирование'), 'Основы синтаксиса', 1, 'Изучаем базовый синтаксис.'),
(nextval('modules_seq'), (SELECT id FROM courses WHERE title = 'Программирование'), 'Структуры данных', 2, 'Работа с массивами и списками.');

-- Занятия для первого модуля курса "Математика": Алгебра
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Алгебра'), 'Умножение и деление', 'Базовые операции умножения и деления.', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Алгебра'), 'Простые уравнения', 'Решаем простейшие линейные уравнения.', '');

-- Занятия для второго модуля курса "Математика": Геометрия
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Геометрия'), 'Окружность', 'Свойства окружности.', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Геометрия'), 'Прямоугольники', 'Площадь прямоугольника.', '');

-- Занятия для первого модуля курса "Информатика": Компьютерные сети
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Компьютерные сети'), 'IP адреса', 'Что такое IP адрес?', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Компьютерные сети'), 'Протокол TCP/IP', 'Описание протокола передачи данных.', '');

-- Занятия для второго модуля курса "Информатика": Архитектура компьютеров
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Архитектура компьютеров'), 'Процессор', 'Знакомимся с устройством процессора.', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Архитектура компьютеров'), 'Память компьютера', 'Типы памяти и её устройство.', '');

-- Занятия для первого модуля курса "Программирование": Основы синтаксиса
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Основы синтаксиса'), 'Переменные', 'Создание переменных.', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Основы синтаксиса'), 'Типы данных', 'Работаем с разными типами данных.', '');

-- Занятия для второго модуля курса "Программирование": Структуры данных
INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Структуры данных'), 'Массивы', 'Использование массивов.', ''),
(nextval('lessons_seq'), (SELECT id FROM modules WHERE title = 'Структуры данных'), 'Списки', 'Работа со списками.', '');