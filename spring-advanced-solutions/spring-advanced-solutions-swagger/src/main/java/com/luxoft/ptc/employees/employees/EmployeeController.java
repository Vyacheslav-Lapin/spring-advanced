package com.luxoft.ptc.employees.employees;


import com.luxoft.ptc.employees.model.Employee;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
@Tag(name = "Employee", description = "Employee Controller")
public class EmployeeController implements RepresentationModelProcessor<RepositoryLinksResource> {

    EmployeeRepository employeeRepository;

    @GetMapping(path="findByExample")
    public Iterable<Employee> emptyGet() {
        return null;
    }

    @PostMapping(path="findByExample")
    public Iterable<Employee> findByExample(@RequestBody Employee probe) {
        return employeeRepository.findByExample(probe);
    }

    @Operation(description = "Get id of the employee's manager")
    @RequestMapping(value="{id}/managerId", method = RequestMethod.GET)
    public Long getManagerId(@PathVariable("id") Long empId) {
        Employee employee = employeeRepository.findOne(empId).orElseThrow();
        if (employee.getManager() == null)
            throw new RestClientException("Manager is not set");
        return employee.getManager().getId();
    }

    @ApiOperation(value = "Set id of the employee's manager")
    @RequestMapping(value="{id}/managerId", method = RequestMethod.POST)
    public void setManagerId(@PathVariable("id") Long empId,
                             Long id) {
        Employee employee = employeeRepository.findOne(empId);
        Employee manager = employeeRepository.findOne(id);
        if (manager == null) {
            throw new RestClientException("Manager with id "+id+" was not found");
        }
        employee.setManagerId(id);
        employeeRepository.save(employee);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        //final String search = resource.getId().getHref();
        resource.add(ControllerLinkBuilder
                .linkTo(methodOn(EmployeeController.class)
                        .findByExample(null))
                .withRel("employeesByExample")
        );

        return resource;
    }
}
