
REST-Сервер для работы Employees и Tasks (работа в классе и домашняя работа)

Собранный сервер со всеми зависимостями можно скачать тут:
https://yadi.sk/d/mlHKb1cX3ZzQ6v
(также его можно собрать из исходников EmployeesBackend командой mvnw package, 
затем взять в папке target/employees-1.0.0-SNAPSHOT.jar и переименовать в RestServer.jar)

Сервер RestServer.jar после скачивания поместить в папку Server.

Для запуска севера перейдите в папку Server и наберите в консоли:
java -jar RestServer.jar

Сервер запустится на порту 3333.
База данных проекта находится в файле employees_db.mv.db

Сервер поддерживает CROSS Origin запросы, таким образом клиент 
может быть запущен под любым внешним севером.

После старта по адресу http://localhost:3333 будет запущен HAL Browser 
для просмотра и выполнения REST запросов (работает как HATEOAS).

По адресу http://localhost:3333/swagger-ui.html можно посмотреть 
документацию REST-сервиса в формате Swagger.

http://localhost:3333/h2-console - консоль Базы Данных, 
можно подключиться и посмотреть/изменить таблицы EMPLOYEES и TASKS

В папке EmployeesBackend находится исходный код сервера.
Использованы технологии Spring Boot, Spring Rest, Spring Data JPA, 
SpringFox (для генерации Swagger документации).

(C) Luxoft, Vladimir Sonkin, 2018
