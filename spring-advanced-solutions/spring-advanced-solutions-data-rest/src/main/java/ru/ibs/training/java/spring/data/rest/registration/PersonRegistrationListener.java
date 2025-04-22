package ru.ibs.training.java.spring.data.rest.registration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PersonRegistrationListener {

    @EventListener
    public void confirmRegistration(PersonRegistrationEvent personRegistrationEvent) {
        personRegistrationEvent.person().setRegistered(true);
        System.out.println("Confirming the registration for the person: "
                + personRegistrationEvent.person());
    }
}
