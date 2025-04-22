package com.luxoft.data.examples.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PERSON")
@RequiredArgsConstructor
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection",
                   "com.haulmont.ampjpb.LombokDataInspection"})
public class Person {

    @Id @GeneratedValue Long id;

    @Column(name = "NAME", nullable = false)
    @NonNull String name;

    @Min(value = 15, message = "Age should not be less than 15")
    @Max(value = 120, message = "Age should not be greater than 120")
    @Column(name = "AGE")
    @NonNull Integer age;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NonNull Address address;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Person person = (Person) o;
    return getId() != null && Objects.equals(getId(), person.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
