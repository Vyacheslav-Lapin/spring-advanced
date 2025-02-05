package com.luxoft.springadvanced.webflux.controller;

import com.luxoft.springadvanced.webflux.model.Person;
import com.luxoft.springadvanced.webflux.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class PersonController {
  PersonService personService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/custom-persons",
              produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Person> getPersons() {
    return personService.findAll();
  }
}
