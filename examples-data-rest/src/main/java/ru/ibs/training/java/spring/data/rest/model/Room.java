package ru.ibs.training.java.spring.data.rest.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Room {

    @ToString.Include @NonNull String roomNumber;
    @NonNull int seats;
    Set<Person> persons = new HashSet<>();

    public Set<Person> getPersons() {
        return Collections.unmodifiableSet(persons);
    }

    public boolean addPerson(Person person) {
        if (persons.size() >= seats)
          throw new RuntimeException("Cannot add more persons than the capacity of the room!");
        return persons.add(person);
    }

    public boolean removePerson(Person person) {
        return persons.remove(person);
    }
}
