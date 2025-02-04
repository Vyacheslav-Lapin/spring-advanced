package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.configs.CsvDataLoader;
import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.dao.CountryRepository;
import com.luxoft.springadvanced.springdatarest.dao.PersonRepository;
import com.luxoft.springadvanced.springdatarest.model.Room;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.Map;

@SpringBootApplication
@Import(CsvDataLoader.class)
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
    public FilterRegistrationBean<ShallowEtagHeaderFilter> someFilterRegistration() {
        val registration = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
        registration.addUrlPatterns("/*");
        registration.setName("etagFilter");
        return registration;
    }

    @Bean(name = "etagFilter")
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
