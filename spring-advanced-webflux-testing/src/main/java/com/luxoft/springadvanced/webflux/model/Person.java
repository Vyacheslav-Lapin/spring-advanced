package com.luxoft.springadvanced.webflux.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Person implements Serializable {

  @Serial
  private static final long serialVersionUID = 8885925356905145451L;

  @Id @NonNull Long id;
  @NonNull String name;
  @NonNull LocalDate birthDate;
  @NonNull Country country;
  @NonNull Boolean isRegistered;
  @NonNull Boolean isUSACitizen;
}
