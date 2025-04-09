package com.luxoft.springadvanced.springrest.beans;

import com.fasterxml.jackson.databind.MappingIterator;
import com.luxoft.springadvanced.springrest.model.Country;
import com.luxoft.springadvanced.springrest.model.Flight;
import com.luxoft.springadvanced.springrest.model.Passenger;
import io.vavr.control.Try;
import lombok.val;
import org.springframework.context.annotation.Bean;
import ru.ibs.training.java.spring.core.CsvUtils;

import java.util.Collection;
import java.util.Map;

import static java.util.function.UnaryOperator.*;
import static java.util.stream.Collectors.*;

public class FlightBuilder {

  @Bean
  Map<String, Country> countriesMap() {
    return Try.withResources(() -> CsvUtils.readFile("/countries_information.csv", Country.class))
              .of(MappingIterator::readAll)
              .map(Collection::stream)
              .map(countryDtos -> countryDtos.collect(toUnmodifiableMap(Country::getCodeName, identity())))
              .getOrElseThrow(ex -> new RuntimeException("Cannot read countries from csv", ex));
  }

  @Bean
  Flight buildFlightFromCsv() {

    val flight = new Flight("AA1234", 20);

    Try.withResources(() -> CsvUtils.readFile("/flights_information.csv"))
       .of(MappingIterator::readAll)
       .getOrElseThrow(ex -> new RuntimeException("Cannot read passengers from csv"))
       .stream()
       .map(strings -> new Passenger(strings.getFirst())
           .setCountry(countriesMap().get(strings.get(1).trim())))
       .forEach(flight::addPassenger);

    return flight;
  }
}
