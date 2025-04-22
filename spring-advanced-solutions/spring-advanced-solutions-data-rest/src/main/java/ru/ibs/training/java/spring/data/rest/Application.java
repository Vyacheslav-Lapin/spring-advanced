package ru.ibs.training.java.spring.data.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import ru.ibs.training.java.spring.data.rest.beans.BeansBuilder;
import ru.ibs.training.java.spring.data.rest.dao.CountryRepository;
import ru.ibs.training.java.spring.data.rest.dao.PersonRepository;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Room;

import java.util.Map;

@SpringBootApplication
@Import(BeansBuilder.class)
@RequiredArgsConstructor
public class Application {

  Room room;

  Map<String, Country> countriesMap;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  ApplicationRunner configureRepository(CountryRepository countryRepository,
                                        PersonRepository personRepository) {
    return args -> {
      countryRepository.saveAll(countriesMap.values());
      personRepository.saveAll(room.getPersons());
    };
  }

  @Bean
  public FilterRegistrationBean<ShallowEtagHeaderFilter> someFilterRegistration(ShallowEtagHeaderFilter etagFilter) {
    val registration = new FilterRegistrationBean<>(etagFilter);
    registration.addUrlPatterns("/*");
    registration.setName("etagFilter");
    return registration;
  }

  @Bean
  public ShallowEtagHeaderFilter etagFilter() {
    return new ShallowEtagHeaderFilter();
  }

}
