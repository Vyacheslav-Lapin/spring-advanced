package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.beans.BeansBuilder;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.model.Room;
import com.luxoft.springadvanced.springdatarest.registration.PersonRegistrationEvent;
import com.luxoft.springadvanced.springdatarest.registration.RegistrationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
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

        log.info("All persons from the room are now confirmed as registered");

        for (Person person : room.getPersons())
          assertTrue(person.isRegistered());
    }
}
