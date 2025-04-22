package ru.ibs.training.java.spring.data.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.training.java.spring.data.rest.dao.PersonRepository;
import ru.ibs.training.java.spring.data.rest.exceptions.PersonNotFoundException;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Person;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PersonController {

    final PersonRepository repository;
    final Map<String, Country> countriesMap;

    @GetMapping("/persons")
    List<Person> findAll() {
        return repository.findAll();
    }

    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    Person createPerson(@RequestBody Person person) {
        return repository.save(person);
    }

    @GetMapping("/persons/{id}")
    Person findPerson(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PatchMapping("/persons/{id}")
    Person patchPerson(@RequestBody Map<String, String> updates, @PathVariable Long id) {

        return repository.findById(id)
                .map(person -> {

                    String name = updates.get("name");
                    if (null != name) {
                        person.setName(name);
                    }

                    Country country = countriesMap.get(updates.get("country"));
                    if (null != country) {
                        person.setCountry(country);
                    }

                    String isRegistered = updates.get("isRegistered");
                    if (null != isRegistered) {
                        person.setIsRegistered(isRegistered.equalsIgnoreCase("true"));
                    }
                    return repository.save(person);
                })
                .orElseThrow(() -> new PersonNotFoundException(id));

    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
