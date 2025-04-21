package com.luxoft.springadvanced.springdatarest.controllers;

import com.luxoft.springadvanced.springdatarest.dao.CountryRepository;
import com.luxoft.springadvanced.springdatarest.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("country")
public class CountryController {

  CountryRepository countryRepository;

  @PostMapping
  ResponseEntity<String> createCountry(Country country) {
    countryRepository.save(country);
    return ResponseEntity.ok("Hello from CountryController!");
  }
}
