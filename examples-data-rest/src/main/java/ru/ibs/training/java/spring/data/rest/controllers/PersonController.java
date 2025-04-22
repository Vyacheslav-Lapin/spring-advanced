package ru.ibs.training.java.spring.data.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
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
import ru.ibs.training.java.spring.data.rest.registration.PersonRegistrationEvent;
import ru.ibs.training.java.spring.data.rest.reloader.Run;
import ru.ibs.training.java.spring.data.rest.services.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PersonController {

  ApplicationContext context;
  PersonRepository repository;
  PersonService personService;

  // TODO: uncomment and check why it is not working
  // probably we need to reload Bean which creates countriesMap
  //Map<String, Country> countriesMap;

  @GetMapping("/check5")
  @Run("/check5")
  String check() {
    return "Catalin, it works: " + personService.getService7();
  }

  @GetMapping("/catalin")
  @Run("/catalin?hi=hi")
  String catalin(String hi) {
    return "Catalin, it works! " + hi;
  }

  @GetMapping("/check4/{smth}")
  @Run("/check4/Vladimir")
  String check3(@PathVariable String smth) {
    return "it works, " + smth + "!!";
  }

  @GetMapping("/persons")
  List<Person> findAll() {
    return repository.findAll();
  }

  @PostMapping("/persons")
  @ResponseStatus(HttpStatus.CREATED)
  Person createPerson(@RequestBody Person person) {
    context.publishEvent(new PersonRegistrationEvent(person));
    return repository.save(person);
  }

  @Run("/persons/4")
  @GetMapping("/persons/{id}")
  Person findPerson(@PathVariable Long id) {
    return repository.findById(id)
                     .orElseThrow(() -> new PersonNotFoundException(id));
  }

  @GetMapping("/check6")
  @Run("/check6")
  String check6() {
    return personService.getService();
  }

  @PatchMapping("/persons/{id}")
  Person patchPerson(@RequestBody Map<String, String> updates, @PathVariable Long id) {

    return repository.findById(id)
                     .map(person -> {
                       Optional.ofNullable(updates.get("name")).ifPresent(person::setName);
                       person.setCountry(new Country("", ""));//countriesMap.get(updates.get("country"));
                       Optional.ofNullable(updates.get("isRegistered"))
                           .ifPresent(isRegistered -> person.setRegistered(isRegistered.equalsIgnoreCase("true")));
                       return person;
                     })
                     .map(repository::save)
                     .orElseThrow(() -> new PersonNotFoundException(id));

  }

  @DeleteMapping("/persons/{id}")
  void deletePerson(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
