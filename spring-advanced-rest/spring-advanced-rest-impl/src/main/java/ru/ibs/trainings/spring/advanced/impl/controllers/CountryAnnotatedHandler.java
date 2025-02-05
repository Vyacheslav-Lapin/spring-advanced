package ru.ibs.trainings.spring.advanced.impl.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import ru.ibs.trainings.spring.dto.CountryDto;

@Slf4j
@Component
@RepositoryEventHandler
public class CountryAnnotatedHandler {

  @HandleBeforeCreate
  public void handleBeforeSave(CountryDto country) {
    if (country.codeName().equalsIgnoreCase("NK")) {
      log.info("Country {} is not to be created!", country.name());
      throw new CountryCreationException("NK - нет такой страны!");
    } else
      log.info("Country {} is to be created", country.name());
  }

  @HandleAfterCreate
  public void handleAfterSave(CountryDto country) {
    log.info("Country saved: {}", country.name());
  }
}
