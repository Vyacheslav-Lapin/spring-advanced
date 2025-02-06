package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
public class CountryRepositoryEventHandler {

    @HandleBeforeCreate
    public void handleCountryBeforeCreate(Country country){
        log.info("Country {} is to be created", country);
    }

    @HandleAfterCreate
    public void handleCountryAfterCreate(Country country){
        log.info("Country {} has been created", country);
    }

}
