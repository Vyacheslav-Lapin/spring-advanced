package com.luxoft.springadvanced.caching.model;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "addresses")
public class ClientService {

    @Cacheable(cacheNames = "addresses", key = "#client.name")
    public String getCacheableAddress(Client client) {
        return client.getAddress();
    }

    @Cacheable({ "addresses", "directory" })
    public String getCacheableAddressMultipleCaches(Client client) {
        return client.getAddress();
    }

    @CacheEvict(cacheNames = "addresses", allEntries = true)
    public String getAddressCacheEvict(Client client) {
        return client.getAddress();
    }

    @Caching(evict = { @CacheEvict("addresses"),
                       @CacheEvict(value = "directory", key = "#client.name") })
    public String getAddressCacheEvictSelectively(Client client) {
        return client.getAddress();
    }

    @Cacheable
    public String getAddressCacheableNoParameters(Client client) {
        return client.getAddress();
    }

    @CachePut(cacheNames = "addresses", condition = "#client.name=='John'")
    public String getAddressCachePutCondition(Client client) {
        return client.getAddress();
    }

    @CacheEvict(cacheNames = "addresses", allEntries = true)
    public String getPhoneCacheEvict(Client client) {
        return client.getPhone();
    }

}
