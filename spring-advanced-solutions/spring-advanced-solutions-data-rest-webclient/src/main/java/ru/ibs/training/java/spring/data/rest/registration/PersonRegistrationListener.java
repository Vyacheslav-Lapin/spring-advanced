package ru.ibs.training.java.spring.data.rest.registration;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonRegistrationListener {

  @EventListener
  public void confirmRegistration(PersonRegistrationEvent personRegistrationEvent) {
    val person = personRegistrationEvent.person()
                                        .setRegistered(true);

    log.info("Confirming the registration for the person: {}", person);
  }
}
