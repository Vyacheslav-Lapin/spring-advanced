package com.luxoft.springadvanced.springdatarest;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * Утилитарный класс для перезагрузки и компиляции классов Java во время выполнения программы.
 * Предоставляет методы для вызова операции перезагрузки класса путем выполнения GET-запроса к
 * указанной конечной точке URL и определения времени выполнения.
 */
@Slf4j
@UtilityClass
public class ReloadClassTool {

  /**
   * Основной метод для обработки компиляции и перезагрузки указанного файла.
   * Этот метод подключается к конечной точке локального сервера, запускает действие
   * перезагрузки и регистрирует время выполнения процесса.
   *
   * @param args аргументы командной строки, где первым элементом должно быть имя файла для
   *            компиляции и перезагрузки.
   */
  public void main(String[] args) {
    val start = System.nanoTime();
    val file = args[0];
    log.info("Need to compile and reload file {}", file);
    doGet("http://localhost:8081/reload?file=" + file);
    val end = System.nanoTime();
    log.info("Executed in {}ms", (end - start) / 1_000_000);
  }

  @SneakyThrows
  private void doGet(String urlStr) {
    @Cleanup("disconnect") val con = createHttpURLConnection(urlStr);
    @Cleanup val in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    log.info("Response code (status) = {}", con.getResponseCode());
    val content = in.lines().collect(Collectors.joining("\n"));
    log.info(content);
  }

  @SneakyThrows
  private @NotNull HttpURLConnection createHttpURLConnection(String urlStr) {
    if (!(URI.create(urlStr).toURL().openConnection() instanceof HttpURLConnection con))
      throw new IllegalArgumentException("urlStr argument has unsupported HTTP URL value: %s".formatted(urlStr));
    con.setRequestMethod("GET");
    return con;
  }
}
