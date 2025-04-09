package ru.ibs.trainings.spring.stub;

import com.fasterxml.jackson.databind.MappingIterator;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

import static ru.ibs.training.java.spring.core.CsvUtils.*;

@Primary
@Component
@RequiredArgsConstructor
@Accessors(fluent = true)
public class CountryClientStub implements CountryController {

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  List<CountryDto> countries = readCountries();

  @SneakyThrows
  @SuppressWarnings("java:S125")
  private static List<CountryDto> readCountries() {

//    @Cleanup val readCountries = CsvUtils.readFile("/countries_information.csv", CountryDto.class);
//    return readCountries.readAll();

    return Try.withResources(() -> readFile("/countries_information.csv", CountryDto.class))
              .of(MappingIterator::readAll)
              .getOrElseGet(__ -> List.of());
  }

  @Override
  public ResponseEntity<List<CountryDto>> findAll() {
    val countryDtos = countries();
    return countryDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(countryDtos);
  }

  @Override
  public ResponseEntity<CountryDto> save(CountryDto countryDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(countryDto);
  }
}
