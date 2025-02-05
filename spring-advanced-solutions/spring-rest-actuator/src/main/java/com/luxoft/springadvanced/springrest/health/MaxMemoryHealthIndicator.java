package com.luxoft.springadvanced.springrest.health;

import lombok.val;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

@Service
public class MaxMemoryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        val maxMemory = Runtime.getRuntime().maxMemory();
        val neededMemory = 10 * 1024 * 1024L;
        val invalid = maxMemory < neededMemory;
        val status = invalid ? Status.DOWN : Status.UP;
        return Health.status(status).build();
    }
}
