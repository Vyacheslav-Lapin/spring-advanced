package com.luxoft.data.examples.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static lombok.AccessLevel.*;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "ADDRESS")
@NoArgsConstructor(access = PROTECTED)
@SuppressWarnings({"com.haulmont.ampjpb.LombokDataInspection",
                   "com.intellij.jpb.LombokDataInspection"})
public class Address {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(name = "STREET", nullable = false)
    @NonNull String street;

    @Column(name = "CITY", nullable = false)
    @NonNull private String city;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Address address = (Address) o;
    return getId() != null && Objects.equals(getId(), address.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
