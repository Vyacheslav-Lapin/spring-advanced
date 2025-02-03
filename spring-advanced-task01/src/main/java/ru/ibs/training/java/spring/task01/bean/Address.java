package ru.ibs.training.java.spring.task01.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Objects;

import static jakarta.persistence.GenerationType.*;
import static ru.ibs.training.java.spring.task01.utils.HibernateUtils.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection",
                   "com.haulmont.ampjpb.LombokDataInspection",
                   "java:S1700"})
public class Address {

  @Id @GeneratedValue(strategy = IDENTITY)
  Integer id;

  @NonNull String address;
  @NonNull String city;
  @NonNull String state;
  @NonNull String country;
  @NonNull Date created;

  @Override
  public final boolean equals(Object o) {
    return this == o || o instanceof Address anotherAddress
                        && effectiveClass(this) == effectiveClass(anotherAddress)
                        && getId() != null
                        && Objects.equals(getId(), anotherAddress.getId());
  }

  @Override
  public final int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
