package ru.ibs.training.java.spring.data.rest.events;

import ru.ibs.training.java.spring.data.rest.model.Country;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
public class CountryRepositoryEventHandler {

    @HandleBeforeCreate
    public void handleCountryBeforeCreate(Country country){

      if (country.getCodeName().equals("NK"))
        throw new CountryCodeNameIsUnsupportedException("Country code name is NK!");

      log.info("Country {} is to be created", country);
    }

    @HandleAfterCreate
    public void handleCountryAfterCreate(Country country){
        log.info("Country {} has been created", country);
    }

}

@StandardException
class CountryCodeNameIsUnsupportedException extends RuntimeException {}
