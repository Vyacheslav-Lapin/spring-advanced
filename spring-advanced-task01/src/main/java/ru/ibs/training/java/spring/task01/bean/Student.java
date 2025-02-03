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
@RequiredArgsConstructor
@DiscriminatorValue("1")
@Table(name="PERSON_STUDENT")
@NoArgsConstructor(access = PROTECTED)
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection", "com.haulmont.ampjpb.LombokDataInspection"})
public class Student extends Person {

  @NonNull String schoolName;

  @Override
  public boolean equals(Object o) {
    return this == o || canEqual(o)
                        && o instanceof Student student
                        && effectiveClass(this) == effectiveClass(student)
                        && getId() != null
                        && Objects.equals(getId(), student.getId());
  }

  @Override
  protected boolean canEqual(Object other) {
    return other instanceof Student;
  }

  @Override
  public int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
