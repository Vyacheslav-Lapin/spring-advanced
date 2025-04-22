package ru.ibs.training.java.spring.data.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@SuppressWarnings({"com.haulmont.ampjpb.LombokDataInspection", "com.intellij.jpb.LombokDataInspection"})

@Data
@Entity
@NoArgsConstructor // avoid "No default constructor for entity"
@RequiredArgsConstructor
public class Person {

  @Id @GeneratedValue Long id;
  @NonNull String name;
  @ManyToOne Country country;
  boolean isRegistered;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return isRegistered == person.isRegistered &&
           Objects.equals(name, person.name) &&
           Objects.equals(country, person.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, country, isRegistered);
  }
}
