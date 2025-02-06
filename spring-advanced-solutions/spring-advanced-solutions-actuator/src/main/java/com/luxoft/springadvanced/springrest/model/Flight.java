package com.luxoft.springadvanced.springrest.model;

import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.*;

@Value
@ToString(onlyExplicitlyIncluded = true)
public class Flight {

  @ToString.Include String flightNumber;
  @Getter(value = NONE) int seats;
  Set<Passenger> passengers = new HashSet<>();

  public Set<Passenger> getPassengers() {
    return Collections.unmodifiableSet(passengers);
  }

  public boolean addPassenger(Passenger passenger) {
    if (passengers.size() >= seats)
      throw new RuntimeException("Cannot add more passengers than the capacity of the flight!");
    return passengers.add(passenger);
  }

  public boolean removePassenger(Passenger passenger) {
    return passengers.remove(passenger);
  }
}
