package ru.ibs.training.java.spring.advanced.mockito.web;

import lombok.experimental.StandardException;

import java.io.InputStream;

public interface ConnectionFactory {
    InputStream getData() throws ConnectionFactoryException;
}

@StandardException
class ConnectionFactoryException extends RuntimeException {
}
