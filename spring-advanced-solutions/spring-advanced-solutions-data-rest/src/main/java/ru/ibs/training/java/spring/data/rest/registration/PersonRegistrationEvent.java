package ru.ibs.training.java.spring.data.rest.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.NonFinal;
import org.springframework.context.ApplicationEvent;
import ru.ibs.training.java.spring.data.rest.model.Person;

@Setter
@Getter
public class PersonRegistrationEvent extends ApplicationEvent {

    @NonFinal Person person;

    public PersonRegistrationEvent(Person person) {
        super(person);
        this.person = person;
    }
}
