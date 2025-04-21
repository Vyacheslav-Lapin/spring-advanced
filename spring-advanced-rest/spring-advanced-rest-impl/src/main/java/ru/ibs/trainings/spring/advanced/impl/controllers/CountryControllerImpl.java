package ru.ibs.trainings.spring.advanced.impl.controllers;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.advanced.impl.services.CountryService;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@ExtensionMethod(value = CountryMapper.class, suppressBaseMethods = false)
public class CountryControllerImpl implements CountryController {

  CountryService service;

  @Override
  public ResponseEntity<List<CountryDto>> findAll() {
    val countryDtos = service.findAll();
    return countryDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(countryDtos);
  }

  @Override
  public ResponseEntity<CountryDto> save(@NotNull CountryDto countryDto) {
    val saved = service.save(countryDto.toCountryEntity());
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(saved.toCountryDto());
  }
}
