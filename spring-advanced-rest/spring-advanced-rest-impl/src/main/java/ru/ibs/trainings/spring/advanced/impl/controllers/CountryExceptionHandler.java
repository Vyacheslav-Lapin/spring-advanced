package ru.ibs.trainings.spring.advanced.impl.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CountryExceptionHandler {

  @ExceptionHandler(CountryCreationException.class)
  public void handleException(CountryCreationException exception) {
    log.error("Exception: ", exception);
  }
}
