package ru.ibs.training.java.spring.data.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.ibs.training.java.spring.data.rest.beans.BeansBuilder;
import ru.ibs.training.java.spring.data.rest.dao.CountryRepository;
import ru.ibs.training.java.spring.data.rest.dao.PersonRepository;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Room;

import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Import(BeansBuilder.class)
public class Application {

  Room room;

  Map<String, Country> countriesMap;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  ApplicationRunner configureRepository(CountryRepository countryRepository,
                                        PersonRepository personRepository) {
    return __ -> {
      countryRepository.saveAll(countriesMap.values());
      personRepository.saveAll(room.getPersons());
    };
  }

}
