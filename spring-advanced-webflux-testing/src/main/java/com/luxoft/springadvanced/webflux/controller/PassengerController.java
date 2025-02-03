package com.luxoft.springadvanced.webflux.controller;

import com.luxoft.springadvanced.webflux.model.Passenger;
import com.luxoft.springadvanced.webflux.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PassengerController {

  PassengerService passengerService;

  @PostMapping({"/create", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Passenger> create(@RequestBody Passenger e) {
    return passengerService.create(e);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Mono<Passenger>> findById(@PathVariable("id") Integer id) {
    Mono<Passenger> e = passengerService.findById(id);
    HttpStatus status = (e != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

//    return (e != null) ?
//           ResponseEntity.ok(e) :
//           ResponseEntity.notFound().build();

    return new ResponseEntity<>(e, status);
  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Passenger> findByName(@PathVariable String name) {
    return passengerService.findByName(name);
  }

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Flux<Passenger> findAll() {
    return passengerService.findAll();
  }

  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Passenger> update(@RequestBody Passenger e) {
    return passengerService.save(e);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> delete(@PathVariable("id") Integer id) {
    return passengerService.deleteById(id);
  }

}
