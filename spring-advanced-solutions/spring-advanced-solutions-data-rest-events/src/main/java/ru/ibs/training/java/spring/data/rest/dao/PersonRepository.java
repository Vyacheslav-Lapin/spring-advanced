package ru.ibs.training.java.spring.data.rest.dao;

import ru.ibs.training.java.spring.data.rest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
