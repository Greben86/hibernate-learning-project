# hibernate-learning-project
Веб-приложение на базе Spring Boot, которое использует Hibernate/JPA для доступа к базе данных PostgreSQL

![Схема приложения](booking-in-spring-cloud.png)

## Возможности
- Регистрация и вход пользователей (JWT) через Booking Service
- Создание бронирований с двухшаговой согласованностью (`PENDING` → `CONFIRMED`/`CANCELLED` с компенсацией)
- Идемпотентность запросов с `requestId`
- Повторы с паузой и таймауты при удалённых вызовах (добавлен Retryer для OpenFeign)
- Подсказки по выбору номера (сортировка по `times_booked`)
- Администрирование пользователей (CRUD) и отелей/номеров (CRUD) для пользователей с правами администратора
- Агрегации: популярность номеров по `times_booked`

## Архитектура и порты
- `module-configuration`: порт 8888, это сервис конфигураций
- `module-discovery`: порт 8761, это Eureka
- `module-gateway`: порт 8080, это точка входа в приложение
- `module-booking`: порт 8081, регистрируется в Eureka под именем `module-booking`
- `module-hotel-management`: порт 8082, регистрируется в Eureka под именем `module-hotel-management`

Gateway маршрутизирует запросы к сервисам по их serviceId через Eureka и прокидывает заголовок `Authorization` с JWT токеном
После старта сервисы регистрируются в Eureka (`http://localhost:8761/eureka/`).

## Требования
- Java 21+
- Gradle
- Docker compose

## Сборка и запуск
1) Собрать проект:
```bash
gradle clean build
```
2) Запустить при помощи docker-compose:
```bash
docker compose up --detach
```

## Конфигурация JWT
Используется симметричный ключ HMAC, секрет задаётся свойством `security.token.signing.key` в
`src/main/resources/application.yaml`, это свойство распространяется на конфигурации всех сервисов
Свойство `security.token.expiration.minutes` задает время жизни токена

## Быстрый сценарий (через Gateway на 8080)
1) Регистрация пользователя
```bash
curl -X POST http://localhost:8080/api/auth/sign/up \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "Viktor",
  "password": "password123"
}'
```
2) Вход и получение JWT
```bash
TOKEN=$(curl -s -X POST "http://localhost:8080/api/auth/sign/in" \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "root",
  "password": "password123"
}' | jq -r .token)
```
3) Создание курса (нужны права TEACHER):


## Основные эндпойнты


## Консоль PGAdmin
- `http://localhost:5050/browser/`, база данных `jdbc:postgresql://localhost:5432/exam_db` логин `admin` пароля `admin`

## Swagger / OpenAPI
- Swagger UI: `http://localhost:8080/swagger-ui/index.html#/`

## Тестирование
- UserControllerTest - тестирование операций с пользователями
  Каждый тест содержит проверку работы каждого эндпойнта, а также негативный сценарий

