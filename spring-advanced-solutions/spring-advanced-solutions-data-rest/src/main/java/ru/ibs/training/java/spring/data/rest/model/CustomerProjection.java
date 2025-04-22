package ru.ibs.training.java.spring.data.rest.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "myprojection", types = Person.class)
public interface CustomerProjection {

   String getFirstname();

   String getLastname();

   @Value("#{target.country.toString()}")
   String getAddress();
}
