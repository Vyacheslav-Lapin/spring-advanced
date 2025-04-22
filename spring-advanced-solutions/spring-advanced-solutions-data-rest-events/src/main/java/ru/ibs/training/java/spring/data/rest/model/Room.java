package ru.ibs.training.java.spring.data.rest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Room {

    @ToString.Include String roomNumber;
    int seats;
    @Singular Set<Person> persons;

//    public Set<Person> getPersons() {
//        return Collections.unmodifiableSet(persons);
//    }

//    public boolean addPerson(Person person) {
//        if (persons.size() >= seats) {
//            throw new RuntimeException("Cannot add more persons than the capacity of the room!");
//        }
//        return persons.add(person);
//    }

//    public boolean removePerson(Person person) {
//        return persons.remove(person);
//    }
}
