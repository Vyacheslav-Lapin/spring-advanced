package com.luxoft.springadvanced.webflux.service;

import com.luxoft.springadvanced.webflux.controller.PassengerController;
import com.luxoft.springadvanced.webflux.dao.PassengerRepository;
import com.luxoft.springadvanced.webflux.model.Passenger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PassengerController.class)
@Import(PassengerServiceImpl.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PassengerControllerTest {

  @MockitoBean
  @NonFinal PassengerRepository repository;

  WebTestClient webClient;

  @Test
  void testCreatePassenger() {
    val passenger = new Passenger()
        .setId(1)
        .setName("John Smith")
        .setCoveredDistance(1_000L);

    Mockito.when(repository.save(passenger))
           .thenReturn(Mono.just(passenger));

    webClient.post()
             .uri("/create")
             .contentType(MediaType.APPLICATION_JSON)
             .body(BodyInserters.fromValue(passenger))
             .exchange()
             .expectStatus().isCreated();

    Mockito.verify(repository, times(1)).save(passenger);
  }

  @Test
  void testGetPassengersByName() {
    val passenger = new Passenger()
        .setId(1)
        .setName("John Smith")
        .setCoveredDistance(1_000L);

    Mockito.when(repository.findByName("John Smith"))
        .thenReturn(Flux.fromIterable(List.of(passenger)));

    webClient.get().uri("/name/{name}", "John Smith")
             .header(HttpHeaders.ACCEPT, "application/json")
             .exchange()
             .expectStatus().isOk()
             .expectBodyList(Passenger.class);

    Mockito.verify(repository, times(1)).findByName("John Smith");
  }

  @Test
  void testGetPassengerById() {
    Passenger passenger = new Passenger()
        .setId(100)
        .setName("John Smith")
        .setCoveredDistance(1_000L);

    Mockito
        .when(repository.findById(100))
        .thenReturn(Mono.just(passenger));

    webClient.get().uri("/{id}", 100)
             .exchange()
             .expectStatus().isOk()
             .expectBody()
             .jsonPath("$.name").isNotEmpty()
             .jsonPath("$.id").isEqualTo(100)
             .jsonPath("$.name").isEqualTo("John Smith")
             .jsonPath("$.coveredDistance").isEqualTo(1000);

    Mockito.verify(repository, times(1)).findById(100);
  }

}
