package com.luxoft.ptc.employees.employees;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.stream.Stream;

@CrossOrigin
@RepositoryRestResource
@Tag(name = "Employee", description = "Employee Entity")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Operation(description = "Save employee")
  <S extends Employee> @NotNull S save(@NonNull S employee);

  @Operation(description = "Delete employee")
  void delete(@NonNull Employee employee);

  Stream<Employee> findByName(@Param("name") String name);

  Stream<Employee> findBySurname(@Param("surname") String surname);

  @Query("select e from Employee e where (e.name=?1 or ?1=null) " +
         "and (e.surname=?2 or ?2=null) " +
         "and (e.manager.id=?3 or ?3=null)")
  Stream<Employee> findByNameAndSurnameAndManagerId(
      @Param("name") String name,
      @Param("surname") String surname,
      @Param("managerId") Long managerId);
}

