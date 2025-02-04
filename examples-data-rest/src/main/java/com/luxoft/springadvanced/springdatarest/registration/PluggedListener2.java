package com.luxoft.springadvanced.springdatarest.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PluggedListener2 implements ApplicationListener<PersonRegistrationEvent> {

    ApplicationContext applicationContext;

    @Value("${spring.main.banner-mode}")
    public String appName;

    public void onApplicationEvent(PersonRegistrationEvent event) {
        System.out.println("1app name = "+appName);
        System.out.println("!WORKS the registration for the person: "
                + event.getPerson());
    }
}
