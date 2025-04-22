package ru.ibs.training.java.spring.data.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.training.java.spring.data.rest.dao.CountryRepository;
import ru.ibs.training.java.spring.data.rest.model.Country;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    CountryRepository repository;

    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Country> findAll() {
        return repository.findAll();
    }

}
