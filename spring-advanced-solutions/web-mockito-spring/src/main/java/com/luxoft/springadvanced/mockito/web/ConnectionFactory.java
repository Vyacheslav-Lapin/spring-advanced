package com.luxoft.springadvanced.mockito.web;

import java.io.InputStream;

public interface ConnectionFactory {
    InputStream getData() throws Exception;
}