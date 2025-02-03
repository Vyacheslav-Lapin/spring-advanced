package ru.ibs.training.java.spring.task01.bean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static jakarta.persistence.DiscriminatorType.*;
import static jakarta.persistence.GenerationType.*;
import static ru.ibs.training.java.spring.task01.utils.HibernateUtils.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = INTEGER)
@SuppressWarnings({"com.haulmont.ampjpb.LombokDataInspection",
                   "com.intellij.jpb.LombokDataInspection"})
public class Person {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  Integer id;

  @NonNull String firstName;
  @NonNull String lastName;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "PERSON_ID", nullable = false)
  @NonNull Set<Address> addresses;

  @NonNull Date created;

  public Optional<Address> findAddressById(Integer id) {
    return Optional.of(addresses)
                   .stream()
                   .flatMap(Set::stream)
                   .filter(address -> Objects.equals(address.getId(), id))
                   .findFirst();
  }

  @Override
  @Contract(value = "null -> false", pure = true)
  public boolean equals(Object o) {
    return this == o || canEqual(o)
                        && o instanceof Person person
                        && effectiveClass(this) == effectiveClass(person)
                        && getId() != null
                        && Objects.equals(getId(), person.getId());
  }

  protected boolean canEqual(Object other) {
    return other instanceof Person;
  }

  @Override
  public int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
