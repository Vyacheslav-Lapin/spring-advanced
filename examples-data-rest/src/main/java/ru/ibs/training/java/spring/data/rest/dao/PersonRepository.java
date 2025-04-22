package ru.ibs.training.java.spring.data.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.training.java.spring.data.rest.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
