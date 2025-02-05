package com.luxoft.springadvanced.jpa.inheritance.dao;

import java.util.Collection;

import com.luxoft.springadvanced.jpa.inheritance.bean.Person;

public interface PersonInheritanceDao {

    public Collection<Person> findPersons();

    public Person save(Person person);

    public void delete(Person person);

}

