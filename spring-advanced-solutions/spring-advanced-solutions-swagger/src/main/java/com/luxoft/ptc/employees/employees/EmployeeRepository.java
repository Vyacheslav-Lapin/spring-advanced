package com.luxoft.ptc.employees.employees;

import com.luxoft.ptc.employees.model.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
@Tag(name = "Employee", description = "Employee Entity")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
        QueryByExampleExecutor<Employee>, EmployeeRepositoryCustom {

    @Operation(description="Save employee")
    Employee save(Employee var1);

    @Operation(description="Delete employee")
    void delete(Employee var1);

    List<Employee> findByName(@Param("name") String name);
    List<Employee> findBySurname(@Param("surname") String surname);

    @Query("select e from Employee e where (e.name=?1 or ?1=null) " +
            "and (e.surname=?2 or ?2=null) " +
            "and (e.manager.id=?3 or ?3=null)")
    List<Employee> findByNameSurnameManager(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("managerId") Long managerId);

}

