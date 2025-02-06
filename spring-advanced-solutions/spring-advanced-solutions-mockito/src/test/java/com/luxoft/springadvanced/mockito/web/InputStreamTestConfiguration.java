package com.luxoft.springadvanced.mockito.web;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class InputStreamTestConfiguration {
    @Bean
    public InputStream inputStream() {
        return Mockito.mock(InputStream.class);
    }
}
