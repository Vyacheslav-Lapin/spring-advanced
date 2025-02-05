package com.luxoft.springadvanced.caching;

import com.luxoft.springadvanced.caching.config.CachingConfig;
import com.luxoft.springadvanced.caching.model.Client;
import com.luxoft.springadvanced.caching.model.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CachingConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringCachingTest {

    @Autowired
    private ClientService service;

    @Test
    public void testCacheableAddress() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getCacheableAddress(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddress(john),
                       () -> "The address should be taken from the cache, so unchanged");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testCacheableAddressMultipleCaches() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getCacheableAddressMultipleCaches(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddressMultipleCaches(john),
                () -> "The address should be taken from the cache, so unchanged");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testAddressCacheEvict() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getAddressCacheEvict(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("New address", service.getAddressCacheEvict(john),
                () -> "The address should be updated");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testAddressCacheEvictSelectively() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getAddressCacheEvictSelectively(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("New address", service.getAddressCacheEvictSelectively(john),
                () -> "The address should be updated");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testAddressCacheableNoParameters() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getAddressCacheableNoParameters(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("Bucharest, Calea Floreasca 167", service.getAddressCacheableNoParameters(john),
                () -> "The address should be taken from the cache, so unchanged");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testAddressCachePutCondition() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        Date date1 = new Date();
        service.getAddressCachePutCondition(john);
        john.setAddress("New address");
        Date date2 = new Date();
        assertEquals("New address", service.getAddressCachePutCondition(john),
                () -> "The address should be updated");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));

        Client mike = new Client("Mike", "Bucharest, Calea Floreasca 167");
        date1 = new Date();
        service.getAddressCachePutCondition(mike);
        mike.setAddress("New address");
        date2 = new Date();
        assertEquals("New address", service.getAddressCachePutCondition(mike),
                () -> "The address should be updated");
        date3 = new Date();
        System.out.println("Duration of the first execution  (address retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (address retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }

    @Test
    public void testPhoneCacheEvict() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        john.setPhone("123467890");
        Date date1 = new Date();
        service.getPhoneCacheEvict(john);
        john.setPhone("0987654321");
        Date date2 = new Date();
        assertEquals("0987654321", service.getPhoneCacheEvict(john),
                () -> "The phone should be updated");
        Date date3 = new Date();
        System.out.println("Duration of the first execution  (phone retrieved from client): " + (date2.getTime()-date1.getTime()));
        System.out.println("Duration of the second execution (phone retrieved from cache): " + (date3.getTime()-date2.getTime()));
    }


}
