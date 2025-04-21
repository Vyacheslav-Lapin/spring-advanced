package com.luxoft.springadvanced.springdatarest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.jetbrains.annotations.Contract;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@SuppressWarnings({"ALL", "java:S2097"})

@Data
@Entity
@Validated
@NoArgsConstructor // avoid "No default constructor for entity"
@AllArgsConstructor
public class Country {

  @Id @NotNull String codeName;
  @NotNull String name;

  @Override
  @Contract(value = "null -> false", pure = true)
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Country country = (Country) o;
    return getCodeName() != null && Objects.equals(getCodeName(), country.getCodeName());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
