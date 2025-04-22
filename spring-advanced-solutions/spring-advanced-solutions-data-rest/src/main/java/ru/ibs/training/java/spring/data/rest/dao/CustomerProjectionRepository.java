package ru.ibs.training.java.spring.data.rest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ibs.training.java.spring.data.rest.model.CustomerProjection;
import ru.ibs.training.java.spring.data.rest.model.Person;

@RepositoryRestResource(excerptProjection = CustomerProjection.class)
public interface CustomerProjectionRepository extends CrudRepository<Person, Long> {
}
