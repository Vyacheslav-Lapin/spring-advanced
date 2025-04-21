package ru.ibs.trainings.spring.advanced.impl.dao;

import io.vavr.CheckedFunction0;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionalExample {

  DataSource dataSource;

  @SneakyThrows
  public <T> Optional<T> doInTransaction(@NotNull CheckedFunction0<T> txBody) {
    // Для запуска транзакций необходимо подключение к базе данных. DriverManager.getConnection(url, user, password) тоже подойдёт, хотя в
    // большинстве корпоративных приложений вы будете иметь настроенный источник данных и получать соединения из него.
    val connection = dataSource.getConnection();

    try (connection) {
      // Это единственный способ "начать" транзакцию базы данных в Java, даже несмотря на то, что название может звучать немного странно.
      // setAutoCommit(true) гарантирует, что каждый SQL-оператор будет автоматически завёрнут в собственную транзакцию, а
      // setAutoCommit(false) - наоборот: Вы являетесь хозяином транзакции (транзакций), и Вам придётся начать вызывать commit и друзей.
      // Обратите внимание, что флаг autoCommit действует в течение всего времени, пока ваше соединение открыто, что означает, что вам
      // нужно вызвать метод только один раз, а не несколько.
      connection.setAutoCommit(false);

      val result = txBody.apply(); // выполняем несколько SQL-запросов...

      connection.commit(); // Зафиксируем транзакцию...

      return Optional.of(result);

    } catch (SQLException e) { // ну а если произошло исключение...
      connection.rollback(); // ...откатим наши изменения
    }

    return Optional.empty();
  }


}
