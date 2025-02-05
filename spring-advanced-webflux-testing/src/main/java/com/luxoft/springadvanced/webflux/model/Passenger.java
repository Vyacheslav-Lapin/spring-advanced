package com.luxoft.springadvanced.webflux.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@Document
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Passenger implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Id int id;
  String name;
  long coveredDistance;
}
