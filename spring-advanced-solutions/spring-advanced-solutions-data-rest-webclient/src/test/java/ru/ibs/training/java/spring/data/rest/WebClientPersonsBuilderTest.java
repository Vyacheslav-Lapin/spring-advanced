package ru.ibs.training.java.spring.data.rest;

import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Person;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtensionMethod(suppressBaseMethods = false,
                 value = BodyExtractors.class)
public class WebClientPersonsBuilderTest {

  @Test
  @Disabled
  void testGetPerson() {
    WebClient webClient = WebClient.builder()
                                   .baseUrl("http://localhost:8081")
                                   .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                                  MediaType.APPLICATION_JSON_VALUE)
                                   .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081"))
                                   .build();

    val clientResponse = webClient.get()
                                  .uri("/persons/1")
                                  .exchangeToMono(Mono::just)
                                  .block();

    val headers = clientResponse.headers();

    Map<String, Object> responseMap = clientResponse.body(Map.class.toMono()).block();
    System.out.println(responseMap);

    assertAll(
        () -> assertEquals("Jack Vaughn", responseMap.get("name")),
        () -> assertEquals("US", ((Map) responseMap.get("country")).get("codeName")),
        () -> assertEquals("USA", ((Map) responseMap.get("country")).get("name")),
        () -> assertEquals(false, responseMap.get("registered")),
        () -> assertEquals(82, headers.contentLength().getAsLong()),
        () -> assertEquals(MediaType.APPLICATION_JSON, headers.contentType().get()),
        () -> assertEquals(HttpStatus.OK, clientResponse.statusCode()));
  }

  @Test
  @Disabled
  void testPostPerson() {
    WebClient webClient = WebClient.builder()
                                   .baseUrl("http://localhost:8081")
                                   .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                                  MediaType.APPLICATION_JSON_VALUE)
                                   .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081"))
                                   .build();
    Person person = new Person();
    person.setName("Michael Stephens");
    person.setCountry(new Country("Australia", "AU"));
    ClientResponse clientResponse = webClient.post()
                                             .uri("/persons")
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .body(BodyInserters.fromObject(person))
                                             .exchange()
                                             .block();

    assertEquals(HttpStatus.CREATED, clientResponse.statusCode());
  }

}
