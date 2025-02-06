package com.luxoft.springadvanced.springdatarest.beans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.model.Room;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.Function;

import static com.luxoft.springadvanced.springdatarest.common.CsvUtils.*;
import static java.util.stream.Collectors.*;

public class BeansBuilder {

  @Getter(onMethod_ = @Bean)
  Map<String, Country> countriesMap;

  @SneakyThrows
  public BeansBuilder() {
    @Cleanup val countryMappingIterator = readFile("/countries_information.csv",
                                                            new TypeReference<Country>() {});
    countriesMap = countryMappingIterator.readAll().stream()
                                         .collect(toMap(Country::getCodeName, Function.identity()));
  }

  @Bean
  @SneakyThrows
  Room buildFlightFromCsv() {
    val flightDtoBuilder = Room.builder()
                                    .roomNumber("AA1234")
                                    .seats(20);

    @Cleanup val passengerMappingIterator = readFile("/persons_information.csv");
    passengerMappingIterator.readAll().stream()
                            .map(strings -> new Person()
                                .setName(strings.getFirst())
                                .setCountry(countriesMap.get(strings.get(1).trim())))
                            .forEach(flightDtoBuilder::person);

    return flightDtoBuilder.build();
  }
}
