package com.luxoft.springadvanced.springdatarest.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class PluggedListener2 implements ApplicationListener<PersonRegistrationEvent> {

    @Autowired
    ApplicationContext applicationContext;

    @Value("${spring.application.name}")
    public String appName;

    public void onApplicationEvent(PersonRegistrationEvent event) {
        System.out.println("app name = "+appName);
        System.out.println("WORKS the registration for the person: "
                + event.getPerson());
    }
}
