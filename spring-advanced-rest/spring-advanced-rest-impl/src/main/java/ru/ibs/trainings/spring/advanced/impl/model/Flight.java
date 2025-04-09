package ru.ibs.trainings.spring.advanced.impl.model;

import jakarta.validation.constraints.Positive;
import lombok.ToString;
import lombok.Value;
import ru.ibs.trainings.spring.advanced.impl.exceptions.AllSeatsAreTakenException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Value
public class Flight {

  String flightNumber;
  @ToString.Exclude @Positive int seats;
  @ToString.Exclude Set<Passenger> passengers = new HashSet<>();

  public Set<Passenger> getPassengers() {
    return Collections.unmodifiableSet(passengers);
  }

  @SuppressWarnings("unused")
  public Flight addPassenger(Passenger passenger) {
    if (passengers.size() >= seats)
      throw new AllSeatsAreTakenException("Cannot add more passengers than the capacity of the flight!");
    passengers.add(passenger);
    return this;
  }

  @SuppressWarnings("unused")
  public boolean removePassenger(Passenger passenger) {
    return passengers.remove(passenger);
  }

  public Flight addAllPassengers(Collection<Passenger> passengers) {
    if (this.passengers.size() + passengers.size() >= seats)
      throw new AllSeatsAreTakenException("Cannot add more passengers than the capacity of the flight!");
    this.passengers.addAll(passengers);
    return this;
  }
}

