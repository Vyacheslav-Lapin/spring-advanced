package ru.ibs.training.java.spring.data.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ibs.training.java.spring.data.rest.events.CountryRepositoryEventHandler;
import ru.ibs.training.java.spring.data.rest.events.PersonRepositoryEventHandler;

@Configuration
public class RepositoryHandlerConfiguration {

    @Bean
    public PersonRepositoryEventHandler personRepositoryEventHandler() {
        return new PersonRepositoryEventHandler();
    }

    @Bean
    public CountryRepositoryEventHandler countryRepositoryEventHandler() {
        return new CountryRepositoryEventHandler();
    }
}
