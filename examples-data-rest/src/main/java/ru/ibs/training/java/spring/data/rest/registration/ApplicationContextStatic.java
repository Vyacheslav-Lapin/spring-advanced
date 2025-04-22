package ru.ibs.training.java.spring.data.rest.registration;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextStatic implements ApplicationContextAware {

  @SuppressWarnings({"java:S1104", "java:S1444"})
  public static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
    ApplicationContextStatic.applicationContext = applicationContext;
  }
}
