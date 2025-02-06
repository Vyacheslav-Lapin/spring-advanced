package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonRepositoryEventListener extends AbstractRepositoryEventListener<Person> {

    @Override
    public void onBeforeCreate(Person person) {
      log.info("I'll make an effort to create this person {}", person.getName());
    }

    @Override
    public void onAfterCreate(Person person) {
      log.info("I did my best and I was able to create {}", person.getName());
    }

    @Override
    public void onBeforeSave(Person person) {
      log.info("I'll take a breath and I will save {}", person.getName());
    }

    @Override
    public void onAfterSave(Person person) {
      log.info("Hard, hard to save {}", person.getName());
    }

    @Override
    public void onBeforeDelete(Person person) {
      log.warn("I'll delete {}, you might never see him/her again", person.getName());
    }

    @Override
    public void onAfterDelete(Person person) {
      log.warn("Say good-bye to {}, I deleted him/her", person.getName());
    }
}
