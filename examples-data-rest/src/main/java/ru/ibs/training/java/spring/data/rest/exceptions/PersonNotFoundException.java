package ru.ibs.training.java.spring.data.rest.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Person id not found : " + id);
    }
}
