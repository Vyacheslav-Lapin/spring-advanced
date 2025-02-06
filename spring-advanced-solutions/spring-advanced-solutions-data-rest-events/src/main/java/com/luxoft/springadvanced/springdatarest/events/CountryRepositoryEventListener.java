package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CountryRepositoryEventListener extends AbstractRepositoryEventListener<Country> {

  @Override
  public void onBeforeCreate(Country country) {
    log.info("I'll make an effort to create this country {}", country);
  }

  @Override
  public void onAfterCreate(Country country) {
    log.info("I did my best and I was able to create {}", country);
  }

}
