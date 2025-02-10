package com.luxoft.ptc.employees.tasks;


import com.luxoft.ptc.employees.employees.EmployeeRepository;
import com.luxoft.ptc.employees.employees.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.springframework.data.domain.Example;
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

import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("tasks")
@Tag(name = "Tasks", description = "Tasks Controller")
@ExtensionMethod(value = WebMvcLinkBuilder.class, suppressBaseMethods = false)
public class TasksController implements RepresentationModelProcessor<RepositoryLinksResource> {

  TasksRepository tasksRepository;
  EmployeeRepository employeeRepository;

  @GetMapping("findByExample")
  public Iterable<Task> emptyGet() {
    return null;
  }

  @PostMapping("findByExample")
  public Iterable<Task> findByExample(@RequestBody Task probe) {

    Optional.ofNullable(probe.getAuthorId())
            .flatMap(employeeRepository::findById)
            .map(probe::setAuthor)
            .orElseThrow(() -> new RestClientException("Employee not found"));

    Optional.ofNullable(probe.getResponsibleId())
            .flatMap(employeeRepository::findById)
            .map(probe::setResponsible)
            .orElseThrow(() -> new RestClientException("Responsible employee not found"));

    return tasksRepository.findAll(Example.of(probe));
  }

  @GetMapping("{id}/authorId")
  @Operation(description = "Get id of the task's author")
  public Long getAuthorId(@PathVariable("id") long taskId) {
    return tasksRepository.findById(taskId)
                          .map(Task::getAuthor)
                          .map(Employee::getId)
                          .orElseThrow(() -> new RestClientException("Author is not set"));
  }

  @PostMapping("{taskId}/authorId")
  @Operation(description = "Set id of the task's author")
  public void setAuthorId(@PathVariable long taskId, long authorId) {
    if (employeeRepository.existsById(authorId))
      tasksRepository.findById(taskId)
                     .map(aTask -> aTask.setAuthorId(authorId))
                     .map(tasksRepository::save)
                     .orElseThrow(() -> new RestClientException("Author with id %d was not found".formatted(authorId)));
  }

  @GetMapping("{id}/responsibleId")
  @Operation(description = "Get id of the task's author")
  public Long getResponsibleId(@PathVariable("id") long taskId) {
    return tasksRepository.findById(taskId)
                          .map(Task::getResponsible)
                          .map(Employee::getId)
                          .orElseThrow(() -> new RestClientException("Responsible is not set"));
  }

  @PostMapping("{id}/responsibleId")
  @Operation(description = "Set id of the task's author")
  public void setResponsibleId(@PathVariable("id") long taskId, long responsibleId) {
    if (employeeRepository.existsById(responsibleId))
      tasksRepository.findById(taskId)
                     .map(task -> task.setResponsibleId(responsibleId))
                     .map(tasksRepository::save)
                     .orElseThrow(() -> new RestClientException(
                         "Responsible with id %d was not found".formatted(responsibleId)));
  }

  @Override
  public RepositoryLinksResource process(RepositoryLinksResource resource) {
    val tasks = WebMvcLinkBuilder.methodOn(TasksController.class)
                                 .findByExample(null)
                                 .linkTo()
                                 .withRel("tasksByExample");
    return resource.add(tasks);
  }
}
