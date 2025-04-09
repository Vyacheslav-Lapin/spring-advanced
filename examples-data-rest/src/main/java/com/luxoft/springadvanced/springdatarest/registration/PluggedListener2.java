package com.luxoft.springadvanced.springdatarest.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PluggedListener2 implements ApplicationListener<PersonRegistrationEvent> {

    @Value("${spring.main.banner-mode}")
    public String appName;

    public void onApplicationEvent(PersonRegistrationEvent event) {
      log.info("1app name = {}", appName);
      log.info("!WORKS the registration for the person: {}", event.person());
    }
}
