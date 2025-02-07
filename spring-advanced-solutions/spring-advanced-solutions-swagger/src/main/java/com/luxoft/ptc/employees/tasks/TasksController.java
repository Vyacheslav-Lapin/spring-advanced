package com.luxoft.ptc.employees.tasks;


import com.luxoft.ptc.employees.employees.EmployeeRepository;
import com.luxoft.ptc.employees.model.Employee;
import com.luxoft.ptc.employees.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@CrossOrigin
@RequestMapping("tasks")
@Tag(description = "Tasks Controller")
@RequiredArgsConstructor
public class TasksController implements RepresentationModelProcessor<RepositoryLinksResource> {

    TasksRepository tasksRepository;
    EmployeeRepository employeeRepository;

    @RequestMapping(value="findByExample", method = RequestMethod.GET)
    public Iterable<Task> emptyGet() {
        return null;
    }

    @RequestMapping(value="findByExample", method = RequestMethod.POST)
    public Iterable<Task> findByExample(@RequestBody Task probe) {
        if (probe.getAuthorId() != null) {
            Employee author = employeeRepository.findOne(probe.getAuthorId()).orElseThrow();
            if (author == null) {
                throw new RestClientException("Author with id "+probe.getAuthorId()+" was not found");
            }
            probe.setAuthor(author);
        }
        if (probe.getResponsibleId() != null) {
            Employee responsible = employeeRepository.findOne(probe.getResponsibleId()).orElseThrow();
            if (responsible == null) {
                throw new RestClientException("Responsible employee with id "+probe.getResponsibleId()+" was not found");
            }
            probe.setResponsible(responsible);
        }

        return tasksRepository.findAll(Example.of(probe));
    }

    @Operation(description = "Get id of the task's author")
    @RequestMapping(value="{id}/authorId", method = RequestMethod.GET)
    public Long getAuthorId(@PathVariable("id") long taskId) {
        Task task = tasksRepository.findOne(taskId).orElseThrow();
        if (task.getAuthor() == null)
            throw new RestClientException("Author is not set");
        return task.getAuthor().getId();
    }

    @Operation(description = "Set id of the task's author")
    @RequestMapping(value="{id}/authorId", method = RequestMethod.POST)
    public void setAuthorId(@PathVariable("id") long taskId,
                             long authorId) {
        Task task = tasksRepository.findOne(taskId).orElseThrow();
        Employee author = employeeRepository.findOne(authorId).orElseThrow();
        if (author == null) {
            throw new RestClientException("Author with id "+authorId+" was not found");
        }
        task.setAuthorId(authorId);
        tasksRepository.save(task);
    }

    @Operation(description = "Get id of the task's author")
    @RequestMapping(value="{id}/responsibleId", method = RequestMethod.GET)
    public Long getResponsibleId(@PathVariable("id") long taskId) {
        Task task = tasksRepository.findOne(taskId).orElseThrow();
        if (task.getResponsible() == null)
            throw new RestClientException("Responsible is not set");
        return task.getResponsible().getId();
    }

    @Operation(description = "Set id of the task's author")
    @RequestMapping(value="{id}/responsibleId", method = RequestMethod.POST)
    public void setResponsibleId(@PathVariable("id") long taskId,
                            long responsibleId) {
        Task task = tasksRepository.findOne(taskId).orElseThrow();
        Employee responsible = employeeRepository.findOne(responsibleId).orElseThrow();
        if (responsible == null) {
            throw new RestClientException("Responsible with id "+responsibleId+" was not found");
        }
        task.setResponsibleId(responsibleId);
        tasksRepository.save(task);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(TasksController.class)
                        .findByExample(null))
                .withRel("tasksByExample")
        );

        return resource;
    }
}
