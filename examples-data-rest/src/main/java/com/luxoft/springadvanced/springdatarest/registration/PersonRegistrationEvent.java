package com.luxoft.springadvanced.springdatarest.registration;

import com.luxoft.springadvanced.springdatarest.model.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.NonFinal;
import org.springframework.context.ApplicationEvent;

public class PersonRegistrationEvent extends ApplicationEvent {

  @Getter
  @Setter
  @NonFinal Person person;

  public PersonRegistrationEvent(Person person) {
    super(person);
    this.person = person;
  }
}
