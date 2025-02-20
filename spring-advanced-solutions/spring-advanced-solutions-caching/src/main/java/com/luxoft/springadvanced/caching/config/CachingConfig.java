package com.luxoft.springadvanced.caching.config;

import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
@ComponentScan("com.luxoft.springadvanced.caching")
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        val cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("directory"),
                                             new ConcurrentMapCache("addresses")));
        return cacheManager;
    }

}
