package com.luxoft.springadvanced.jpa.inheritance.dao;

import com.luxoft.springadvanced.jpa.inheritance.bean.Person;

import java.util.Collection;

public interface PersonInheritanceDao {

  Collection<Person> findPersons();

  Person save(Person person);

  void delete(Person person);

}

