package ru.ibs.training.java.spring.data.rest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ibs.training.java.spring.data.rest.beans.BeansBuilder;
import ru.ibs.training.java.spring.data.rest.model.Person;
import ru.ibs.training.java.spring.data.rest.model.Room;
import ru.ibs.training.java.spring.data.rest.registration.PersonRegistrationEvent;
import ru.ibs.training.java.spring.data.rest.registration.RegistrationManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(BeansBuilder.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoomTest {

    Room room;

    RegistrationManager registrationManager;

    @Test
    void testPersonsRegistration() {
        for (Person person : room.getPersons()) {
            assertFalse(person.isRegistered());
            registrationManager.getApplicationContext().publishEvent(new PersonRegistrationEvent(person));
        }

        System.out.println("All persons from the room are now confirmed as registered");

        for (Person person : room.getPersons()) {
            assertTrue(person.isRegistered());
        }
    }
}
