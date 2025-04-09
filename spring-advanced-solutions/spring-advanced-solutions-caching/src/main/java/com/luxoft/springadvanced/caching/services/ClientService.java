package com.luxoft.springadvanced.caching.services;

import com.luxoft.springadvanced.caching.model.Client;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "addresses")
public class ClientService {

    @Cacheable(cacheNames = "addresses", key = "#client.name")
    public String getCacheableAddress(Client client) {
        return client.address();
    }

    @Cacheable({ "addresses", "directory" })
    public String getCacheableAddressMultipleCaches(Client client) {
        return client.address();
    }

    @CacheEvict(cacheNames = "addresses", allEntries = true)
    public String getAddressCacheEvict(Client client) {
        return client.address();
    }

    @Caching(evict = { @CacheEvict("addresses"),
                       @CacheEvict(value = "directory", key = "#client.name") })
    public String getAddressCacheEvictSelectively(Client client) {
        return client.address();
    }

    @Cacheable
    public String getAddressCacheableNoParameters(Client client) {
        return client.address();
    }

    @CachePut(cacheNames = "addresses", condition = "#client.name=='John'")
    public String getAddressCachePutCondition(Client client) {
        return client.address();
    }

    @CacheEvict(cacheNames = "addresses", allEntries = true)
    public String getPhoneCacheEvict(Client client) {
        return client.phone();
    }
}
