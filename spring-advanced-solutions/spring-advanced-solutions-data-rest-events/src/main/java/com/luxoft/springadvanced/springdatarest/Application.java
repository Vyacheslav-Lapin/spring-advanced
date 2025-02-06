package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.beans.BeansBuilder;
import com.luxoft.springadvanced.springdatarest.dao.CountryRepository;
import com.luxoft.springadvanced.springdatarest.dao.PersonRepository;
import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
