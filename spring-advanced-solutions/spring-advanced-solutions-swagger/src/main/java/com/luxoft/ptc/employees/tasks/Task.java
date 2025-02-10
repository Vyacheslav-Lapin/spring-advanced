package com.luxoft.ptc.employees.tasks;

import com.luxoft.ptc.employees.employees.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Objects;

import static ru.ibs.training.java.spring.core.HibernateUtils.*;

@Data
@Entity
@FieldDefaults(makeFinal = false)
@SuppressWarnings({"com.intellij.jpb.LombokDataInspection",
                   "com.haulmont.ampjpb.LombokDataInspection",
                   "DefaultAnnotationParam"})
public class Task {

  @Id @GeneratedValue Long id;
  Date created;
  Date finish;
  String title;
  String description;

  @ManyToOne
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  Employee author;

  @ManyToOne
  @JoinColumn(name = "responsible_id", insertable = false, updatable = false)
  Employee responsible;

  @Column(name = "author_id")
  Long authorId;

  @Column(name = "responsible id")
  Long responsibleId;

  @Override
  public final boolean equals(Object o) {
    return this == o || o instanceof Task task
                        && effectiveClass(this) == effectiveClass(task)
                        && getId() != null
                        && Objects.equals(getId(), task.getId());
  }

  @Override
  public final int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
