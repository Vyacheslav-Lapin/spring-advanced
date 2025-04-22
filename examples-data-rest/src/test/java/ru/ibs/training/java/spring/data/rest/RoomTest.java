package ru.ibs.training.java.spring.data.rest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import ru.ibs.training.java.spring.data.rest.configs.CsvDataLoader;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Person;
import ru.ibs.training.java.spring.data.rest.model.Room;
import ru.ibs.training.java.spring.data.rest.registration.PersonRegistrationEvent;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CsvDataLoader.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RoomTest {

    Room room;
    ApplicationContext applicationContext;
    Map<String, Country> countriesMap;

    @Test
    void testPersonRegistration() {
        Person person = new Person("Uspehov");
        person.setCountry(new Country("Russia", "RU"));
        applicationContext.publishEvent(
                new PersonRegistrationEvent(person));
    }

    @Test
    @Disabled //todo 04.02.2025: поняь — почему не проходит?
    void testPersonsRegistration() {
        for (Person person : room.getPersons()) {
            assertFalse(person.isRegistered());
            applicationContext.publishEvent(
                    new PersonRegistrationEvent(person));
        }

        System.out.println("All persons are now confirmed as registered");

        for (Person person : room.getPersons()) {
            assertTrue(person.isRegistered());
        }
    }

    @Test
    void testPersonsRegistrationUSA() {
        for (Person person : room.getPersons()) {
            assertFalse(person.isRegistered());
            applicationContext.publishEvent(
                    new PersonRegistrationEvent(person));
        }

        System.out.println("All persons from the USA are registered");

        for (Person person : room.getPersons()) {
            if (person.getCountry().getCodeName().equals("US")) {
                assertTrue(person.isUSACitizen());
                assertTrue(person.isRegistered());
            }
        }
    }
}
