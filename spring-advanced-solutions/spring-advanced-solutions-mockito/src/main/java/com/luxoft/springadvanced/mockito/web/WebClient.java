package com.luxoft.springadvanced.mockito.web;

import lombok.val;

import java.io.IOException;
import java.io.InputStream;

public class WebClient {

  public String getContent(ConnectionFactory connectionFactory) {
    try (val inputStream = connectionFactory.getData()) {
      return readContent(inputStream);
    } catch (Exception e) {
      return null;
    }
  }

  private String readContent(InputStream inputStream) throws IOException {
    return new String(inputStream.readAllBytes());
  }
}
