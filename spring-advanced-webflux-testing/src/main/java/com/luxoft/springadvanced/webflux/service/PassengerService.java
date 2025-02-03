package com.luxoft.springadvanced.webflux.service;

import com.luxoft.springadvanced.webflux.model.Passenger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassengerService {
  Mono<Passenger> create(Passenger e);

  Mono<Passenger> findById(Integer id);

  Flux<Passenger> findByName(String name);

  Flux<Passenger> findAll();

  Mono<Passenger> save(Passenger e);

  Mono<Void> deleteById(Integer id);
}
