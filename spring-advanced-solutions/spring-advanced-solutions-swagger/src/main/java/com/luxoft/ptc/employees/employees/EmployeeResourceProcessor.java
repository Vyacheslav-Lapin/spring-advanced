package com.luxoft.ptc.employees.employees;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeResourceProcessor
    implements RepresentationModelProcessor<EntityModel<Employee>> {

  @Override
  public EntityModel<Employee> process(EntityModel<Employee> employeeResource) {
//        employeeResource.add(linkTo(
//                methodOn(EmployeeController.class)
//                        .getManagerId(null)
//                )
//                .withRel("managerId"));
    return employeeResource;
  }

}
