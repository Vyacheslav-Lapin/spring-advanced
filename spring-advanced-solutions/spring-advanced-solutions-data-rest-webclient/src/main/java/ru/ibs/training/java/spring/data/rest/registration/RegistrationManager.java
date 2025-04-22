package ru.ibs.training.java.spring.data.rest.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@Accessors(chain = false, fluent = false)
public class RegistrationManager implements ApplicationContextAware {
  @NonFinal ApplicationContext applicationContext;
}
