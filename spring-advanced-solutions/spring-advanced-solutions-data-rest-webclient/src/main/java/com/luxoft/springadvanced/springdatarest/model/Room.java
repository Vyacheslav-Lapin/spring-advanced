package com.luxoft.springadvanced.springdatarest.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.*;

@Data
@ToString(onlyExplicitlyIncluded = true)
public class Room {

  @NonNull @ToString.Include String roomNumber;
  @Getter(NONE) @NonNull Integer seats;
  Set<Person> persons = new HashSet<>();

  public Set<Person> getPersons() {
    return Collections.unmodifiableSet(persons);
  }

  public boolean addPerson(Person person) {
    if (persons.size() >= seats) {
      throw new RuntimeException("Cannot add more persons than the capacity of the room!");
    }
    return persons.add(person);
  }

  public boolean removePerson(Person person) {
    return persons.remove(person);
  }

}
