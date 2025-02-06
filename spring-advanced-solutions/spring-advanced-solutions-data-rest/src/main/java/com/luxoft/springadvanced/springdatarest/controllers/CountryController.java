package com.luxoft.springadvanced.springdatarest.controllers;

import com.luxoft.springadvanced.springdatarest.dao.CountryRepository;
import com.luxoft.springadvanced.springdatarest.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    CountryRepository repository;

    @GetMapping("/countries")
    List<Country> findAll() {
        return repository.findAll();
    }

}
