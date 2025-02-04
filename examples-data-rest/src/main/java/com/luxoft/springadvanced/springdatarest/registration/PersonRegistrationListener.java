package com.luxoft.springadvanced.springdatarest.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonRegistrationListener {

  @EventListener
  public void confirmRegistration(PersonRegistrationEvent personRegistrationEvent) {
    personRegistrationEvent.getPerson().setRegistered(true);
    log.info("Confirming the registration for the person: {}", personRegistrationEvent.getPerson());
  }

  @EventListener(condition = "#personRegistrationEvent.person.country.name=='USA'")
  public void confirmRegistrationUSA(PersonRegistrationEvent personRegistrationEvent) {
    personRegistrationEvent.getPerson().setUSACitizen(true);
    log.info("Confirming the registration for the USA person: {}", personRegistrationEvent.getPerson());
  }
}
