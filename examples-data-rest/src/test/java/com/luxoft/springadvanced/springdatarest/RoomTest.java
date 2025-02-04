package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.configs.CsvDataLoader;
import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Room;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.registration.PersonRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
