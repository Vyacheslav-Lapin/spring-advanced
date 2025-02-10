package com.luxoft.ptc.employees.employees;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
@Tag(name = "Employee", description = "Employee Controller")
@ExtensionMethod(value = WebMvcLinkBuilder.class, suppressBaseMethods = false)
public class EmployeeController implements RepresentationModelProcessor<RepositoryLinksResource> {

  EmployeeService employeeService;

  @GetMapping("findByExample")
  public Iterable<Employee> emptyGet() {
    return null;
  }

  @PostMapping("findByExample")
  public Iterable<Employee> findByExample(@RequestBody Employee probe) {
    return employeeService.findByExample(probe);
  }

  @GetMapping("{employeeId}/managerId")
  @Operation(description = "Get id of the employee's manager")
  public Long getManagerId(@PathVariable Long employeeId) {
    return employeeService.findById(employeeId)
                          .map(Employee::getManager)
                          .map(Employee::getId)
                          .orElseThrow(() -> new RestClientException("Manager is not set"));
  }

  @PostMapping("{employeeId}/managerId")
  @Operation(description = "Set id of the employee's manager")
  public void setManagerId(@PathVariable Long employeeId, Long managerId) {
    employeeService.findById(managerId)
                   .flatMap(__ -> employeeService.findById(employeeId))
                   .map(employee -> employee.setManagerId(managerId))
                   .map(employeeService::save)
                   .orElseThrow(() -> new RestClientException(
                          "Manager with id %d, or employee with id %d was not found"
                              .formatted(managerId, employeeId)));
  }

  @Override
  public RepositoryLinksResource process(RepositoryLinksResource resource) {
    //final String search = resource.getId().getHref();
    val employeesByExample = methodOn(EmployeeController.class)
        .findByExample(null)
        .linkTo()
        .withRel("employeesByExample");
    resource.add(employeesByExample);

    return resource;
  }
}
