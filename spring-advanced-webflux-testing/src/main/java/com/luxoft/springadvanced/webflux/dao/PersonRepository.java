package com.luxoft.springadvanced.webflux.dao;


import com.luxoft.springadvanced.webflux.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, Long> {
}
