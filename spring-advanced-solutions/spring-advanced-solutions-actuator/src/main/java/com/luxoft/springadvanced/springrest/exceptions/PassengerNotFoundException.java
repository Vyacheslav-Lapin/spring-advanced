package com.luxoft.springadvanced.springrest.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(Long id) {
        super("Passenger id not found : " + id);
    }

}
