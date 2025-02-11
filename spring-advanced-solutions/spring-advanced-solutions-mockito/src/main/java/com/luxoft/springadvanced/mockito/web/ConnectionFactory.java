package com.luxoft.springadvanced.mockito.web;

import java.io.InputStream;

@FunctionalInterface
public interface ConnectionFactory {
    InputStream getData() throws Exception;
}
