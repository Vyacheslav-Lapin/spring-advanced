package com.luxoft.data.examples.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class PersonNotFoundException extends RuntimeException {

  public PersonNotFoundException(long id) {
    super("Person with id: %d not found".formatted(id));
  }
}
