package com.luxoft.springadvanced.springrest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor // avoid "No default constructor for entity"
@RequiredArgsConstructor
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection",
                   "com.haulmont.ampjpb.LombokDataInspection"})
public class Passenger {

  @Id @GeneratedValue Long id;
  @NonNull String name;
  @ManyToOne Country country;
  boolean isRegistered;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Passenger passenger = (Passenger) o;
    return getId() != null && Objects.equals(getId(), passenger.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
