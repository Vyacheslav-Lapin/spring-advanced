package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Person;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
public class PersonRepositoryEventHandler {
  
    @HandleBeforeCreate
    public void handlePersonBeforeCreate(Person person){
      val firstChar = person.getName().toUpperCase().charAt(0);
      if (firstChar >= 'A' && firstChar <= 'M')
          log.info("Person {} is to be created, goes to the first part of the alphabet", person.getName());
        else
          log.info("Person {} is to be created, goes to the second part of the alphabet", person.getName());
    }

    @HandleAfterCreate
    public void handlePersonAfterCreate(Person person){
      log.info("I am so tired to have created {}", person.getName());
    }

    @HandleBeforeDelete
    public void handlePersonBeforeDelete(Person person){
      log.warn("This is just to let you know that {} is about to be deleted", person.getName());
    }

    @HandleAfterDelete
    public void handlePersonAfterDelete(Person person){
      log.warn("Sad but true, {} has been deleted", person.getName());
    }

}
