package com.luxoft.ptc.employees.employees;

import com.luxoft.ptc.employees.model.Employee;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class EmployeeResourceProcessor
        implements ResourceProcessor<Resource<Employee>> {

    @Override
    public Resource<Employee> process(Resource<Employee> employeeResource) {
//        employeeResource.add(linkTo(
//                methodOn(EmployeeController.class)
//                        .getManagerId(null)
//                )
//                .withRel("managerId"));
        return employeeResource;
    }

}
