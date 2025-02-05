package com.luxoft.springadvanced.webflux.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

@Data
public final class Country implements Serializable {

  @Serial
  private static final long serialVersionUID = 1952150852700889551L;

  @Id @NonNull String codeName;
  @NonNull String name;
}
