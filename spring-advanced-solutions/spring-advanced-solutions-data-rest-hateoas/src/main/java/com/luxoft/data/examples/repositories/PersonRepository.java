package com.luxoft.data.examples.repositories;

import com.luxoft.data.examples.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
