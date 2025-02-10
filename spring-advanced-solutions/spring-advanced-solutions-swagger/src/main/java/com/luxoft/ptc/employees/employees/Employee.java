package com.luxoft.ptc.employees.employees;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luxoft.ptc.employees.tasks.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;
import static ru.ibs.training.java.spring.core.HibernateUtils.*;

@Data
@Entity
@FieldDefaults(makeFinal = false) // 'false' is a default value for 'makeFinal' @FieldDefaults annotation param, but it overrides by lombok.config in the root of the project, so, to take effect, we must explicitly set it here.
@SuppressWarnings({"com.haulmont.ampjpb.LombokDataInspection",
                   "com.intellij.jpb.LombokDataInspection",
                   "DefaultAnnotationParam"})
public class Employee {

  @Id @GeneratedValue Long id;
  String name;
  String surname;
  Date dateOfBirth;

  @Embedded List<String> phones = new ArrayList<>();

  @Column(name = "manager_id")
  Long managerId;

  @ManyToOne
  @JoinColumn(name = "manager_id", insertable = false, updatable = false)
  Employee manager;

  @JsonIgnore
  @ToString.Exclude
  @Schema(name = "employeeSubordinates", description = "TTTTT")
  @OneToMany(mappedBy = "manager", cascade = REMOVE)
  @Getter(onMethod_ = @Operation(description = "Get Subordinates", tags = "Employee"))
  List<Employee> subordinates;

  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "author", cascade = REMOVE)
  List<Task> createdTasks;

  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "responsible", cascade = REMOVE)
  List<Task> tasks;

  @Override
  public final boolean equals(Object o) {
    return this == o || o instanceof Employee employee
                        && effectiveClass(this) == effectiveClass(employee)
                        && getId() != null
                        && Objects.equals(getId(), employee.getId());
  }

  @Override
  public final int hashCode() {
    return effectiveClass(this).hashCode();
  }
}
