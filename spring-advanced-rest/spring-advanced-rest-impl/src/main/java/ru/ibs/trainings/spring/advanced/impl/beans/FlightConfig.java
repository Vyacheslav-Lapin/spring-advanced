package ru.ibs.trainings.spring.advanced.impl.beans;

import com.fasterxml.jackson.databind.MappingIterator;
import io.vavr.control.Try;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.FlightDto;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.util.Collection;
import java.util.Map;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static ru.ibs.training.java.spring.core.CsvUtils.*;

@Configuration
public class FlightConfig {

  @Bean
  Map<String, CountryDto> countriesMap() {
    return Try.withResources(() -> readFile("/countries_information.csv", CountryDto.class))
       .of(MappingIterator::readAll)
       .map(Collection::stream)
       .map(countryDtos -> countryDtos.collect(toMap(CountryDto::codeName, identity())))
       .getOrElseThrow(ex -> new RuntimeException("Cannot read countries from csv", ex));
  }

  @Bean
  FlightDto buildFlightFromCsv() {
    val flightPassengers =
        Try.withResources(() -> readFile("/flights_information.csv"))
           .of(MappingIterator::readAll)
           .getOrElseThrow(ex -> new RuntimeException("Cannot read passengers from csv"))
           .stream()
           .map(strings -> PassengerDto.builder()
                                       .name(strings.getFirst())
                                       .country(countriesMap().get(strings.get(1).trim())).build())
           .collect(toUnmodifiableSet());

    return FlightDto.builder()
                    .flightNumber("AA1234")
                    .seats(20)
                    .passengers(flightPassengers).build();
  }
}
