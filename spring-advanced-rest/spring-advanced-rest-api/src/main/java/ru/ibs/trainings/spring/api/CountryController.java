package ru.ibs.trainings.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

public interface CountryController {

  @GetMapping("/custom-countries")
  List<CountryDto> findAll();

  @PostMapping("/custom-countries")
  ResponseEntity<CountryDto> save(@RequestBody CountryDto countryDto);
}
