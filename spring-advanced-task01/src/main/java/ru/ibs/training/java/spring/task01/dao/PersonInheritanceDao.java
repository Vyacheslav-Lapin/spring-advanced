package ru.ibs.training.java.spring.task01.dao;

import ru.ibs.training.java.spring.task01.bean.Person;

import java.util.Collection;


public interface PersonInheritanceDao {

  Collection<Person> findPersons();

  Person save(Person person);
}

