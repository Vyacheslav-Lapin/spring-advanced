package com.luxoft.springadvanced.webflux.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Passenger implements Serializable {

  @Serial
  private static final long serialVersionUID = -68580263762540569L;

  @Id @NonNull Integer id;
  @NonNull String name;
  @NonNull Long coveredDistance;
}
