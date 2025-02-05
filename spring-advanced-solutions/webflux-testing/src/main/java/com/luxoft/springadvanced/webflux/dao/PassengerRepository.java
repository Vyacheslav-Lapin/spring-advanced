package com.luxoft.springadvanced.webflux.dao;

import com.luxoft.springadvanced.webflux.model.Passenger;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
 
public interface PassengerRepository extends ReactiveMongoRepository<Passenger, Integer> {
    @Query("{ 'name': ?0 }")
    Flux<Passenger> findByName(final String name);
}