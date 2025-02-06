package com.luxoft.springadvanced.springdatarest.dao;

import com.luxoft.springadvanced.springdatarest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
