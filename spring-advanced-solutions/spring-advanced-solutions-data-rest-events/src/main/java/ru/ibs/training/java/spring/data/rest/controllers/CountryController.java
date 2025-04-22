package ru.ibs.training.java.spring.data.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.training.java.spring.data.rest.dao.CountryRepository;
import ru.ibs.training.java.spring.data.rest.model.Country;

@RestController
@RequiredArgsConstructor
@RequestMapping("country")
public class CountryController {

  CountryRepository countryRepository;

  @PostMapping
  ResponseEntity<String> createCountry(Country country) {
    countryRepository.save(country);
    return ResponseEntity.ok("Country saved!");
  }
}
