package ru.ibs.training.java.spring.task01.bean;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.*;
import static ru.ibs.training.java.spring.task01.utils.HibernateUtils.*;

@Data
@Entity
@DiscriminatorValue("2")
@RequiredArgsConstructor
@Table(name="PERSON_PROFESSIONAL")
@NoArgsConstructor(access = PROTECTED)
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection",
                   "com.haulmont.ampjpb.LombokDataInspection"})
public class Professional extends Person {

  @NonNull String companyName;

  @Override
  public final boolean equals(Object o) {
    return this == o || o instanceof Professional professional && super.equals(professional)
                        && effectiveClass(this) == effectiveClass(professional)
                        && getId() != null
                        && Objects.equals(getId(), professional.getId());
  }

  @Override
  protected boolean canEqual(Object other) {
    return other instanceof Professional;
  }

  @Override
  public final int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
