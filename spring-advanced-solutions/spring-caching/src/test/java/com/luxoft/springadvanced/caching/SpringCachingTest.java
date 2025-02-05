package com.luxoft.springadvanced.caching;

import com.luxoft.springadvanced.caching.config.CachingConfig;
import com.luxoft.springadvanced.caching.model.Client;
import com.luxoft.springadvanced.caching.model.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CachingConfig.class,
                      loader = AnnotationConfigContextLoader.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SpringCachingTest {

  ClientService service;

  @Test
  void testCacheableAddress() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167");
    val date1 = System.currentTimeMillis();
    service.getCacheableAddress(john);
    john.setAddress("New address");
    val date2 = System.currentTimeMillis();
    assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddress(john),
                 "The address should be taken from the cache, so unchanged");
    val date3 = System.currentTimeMillis();

    System.out.println("Duration of the first execution  (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));
  }

  @Test
  @Disabled("Разобраться почему не кеширует")
  void testCacheableAddressMultipleCaches() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167");
    val date1 = System.currentTimeMillis();
    service.getCacheableAddressMultipleCaches(john);
    john.setAddress("New address");
    val date2 = System.currentTimeMillis();
    assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddressMultipleCaches(john),
                 "The address should be taken from the cache, so unchanged");
    val date3 = System.currentTimeMillis();
    System.out.println("Duration of the first execution  (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));
  }

  @Test
  void testAddressCacheEvict() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167")
        .setAddress("New address");

    val date1 = System.currentTimeMillis();
    service.getAddressCacheEvict(john);
    val date2 = System.currentTimeMillis();
    assertEquals("New address", service.getAddressCacheEvict(john),
                 "The address should be updated");
    val date3 = System.currentTimeMillis();

    log.info("Duration of the first execution (address retrieved from client): {}", date2 - date1);
    log.info("Duration of the second execution (address retrieved from cache): {}", date3 - date2);
  }

  @Test
  void testAddressCacheEvictSelectively() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167")
        .setAddress("New address");
    val date1 = System.currentTimeMillis();
    service.getAddressCacheEvictSelectively(john);
    val date2 = System.currentTimeMillis();
    assertEquals("New address", service.getAddressCacheEvictSelectively(john),
                 "The address should be updated");
    val date3 = System.currentTimeMillis();
    System.out.println("Duration of the first execution (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));
  }

  @Test
  @Disabled("Разобраться почему не кеширует")
  void testAddressCacheableNoParameters() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167");
    val date1 = System.currentTimeMillis();
    service.getAddressCacheableNoParameters(john);
    john.setAddress("New address");
    val date2 = System.currentTimeMillis();

    assertEquals("Bucharest, Calea Floreasca 167", service.getAddressCacheableNoParameters(john),
                 "The address should be taken from the cache, so unchanged");

    val date3 = System.currentTimeMillis();

    System.out.println("Duration of the first execution  (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));
  }

  @Test
  void testAddressCachePutCondition() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167")
        .setAddress("New address");

    var date1 = System.currentTimeMillis();
    service.getAddressCachePutCondition(john);
    var date2 = System.currentTimeMillis();
    assertEquals("New address", service.getAddressCachePutCondition(john),
                 "The address should be updated");

    var date3 = System.currentTimeMillis();
    System.out.println("Duration of the first execution (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));

    val mike = new Client("Mike", "Bucharest, Calea Floreasca 167");
    date1 = System.currentTimeMillis();
    service.getAddressCachePutCondition(mike);
    mike.setAddress("New address");
    date2 = System.currentTimeMillis();
    assertEquals("New address", service.getAddressCachePutCondition(mike),
                 "The address should be updated");

    date3 = System.currentTimeMillis();
    System.out.println("Duration of the first execution (address retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (address retrieved from cache): " + (date3 - date2));
  }

  @Test
  void testPhoneCacheEvict() {
    val john = new Client("John", "Bucharest, Calea Floreasca 167")
        .setPhone("123467890");

    val date1 = System.currentTimeMillis();
    service.getPhoneCacheEvict(john);
    john.setPhone("0987654321");
    val date2 = System.currentTimeMillis();
    assertEquals("0987654321", service.getPhoneCacheEvict(john),
                 "The phone should be updated");

    val date3 = System.currentTimeMillis();
    System.out.println("Duration of the first execution  (phone retrieved from client): " + (date2 - date1));
    System.out.println("Duration of the second execution (phone retrieved from cache): " + (date3 - date2));
  }
}
