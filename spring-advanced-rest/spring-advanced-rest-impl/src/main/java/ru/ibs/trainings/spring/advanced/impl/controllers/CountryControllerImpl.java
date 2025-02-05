package ru.ibs.trainings.spring.advanced.impl.controllers;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@ExtensionMethod(value = CountryMapper.class, suppressBaseMethods = false)
public class CountryControllerImpl implements CountryController {

  CountryRepository repository;

  @Override
  public List<CountryDto> findAll() {
    return repository.findAll().stream()
                     .map(country -> CountryDto.builder()
                                               .codeName(country.getCodeName())
                                               .name(country.getName()).build())
                     .toList();
  }

  @Override
  public ResponseEntity<CountryDto> save(CountryDto countryDto) {
    val saved = repository.save(countryDto.toCountryEntity());
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(saved.toCountryDto());
  }
}
